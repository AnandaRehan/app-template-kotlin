package com.example.ui.screens.dashboard

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.data.local.entity.AppItemEntity
import com.example.data.local.entity.ItemCategory
import com.example.data.local.entity.ItemPriority
import com.example.data.repository.AppItemRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

data class DashboardUiState(
    val totalItems: Int = 0,
    val completedItems: Int = 0,
    val favoriteItems: Int = 0,
    val inProgressItems: Int = 0,
    val recentItems: List<AppItemEntity> = emptyList(),
    val activeCategoryFilter: ItemCategory? = null,
    val searchQuery: String = "",
    val isLoading: Boolean = false
)

class DashboardViewModel(
    private val repository: AppItemRepository
) : ViewModel() {

    private val _searchQuery = MutableStateFlow("")
    private val _selectedCategory = MutableStateFlow<ItemCategory?>(null)

    val uiState: StateFlow<DashboardUiState> = combine(
        repository.getAllItems(),
        _searchQuery,
        _selectedCategory
    ) { items, search, category ->
        val filtered = items.filter { item ->
            val matchesCategory = category == null || item.category == category
            val matchesSearch = search.isBlank() ||
                item.title.contains(search, ignoreCase = true) ||
                item.description.contains(search, ignoreCase = true) ||
                item.tags.contains(search, ignoreCase = true)
            matchesCategory && matchesSearch
        }

        val total = items.size
        val completed = items.count { it.isCompleted }
        val favorites = items.count { it.isFavorite }
        val inProgress = items.count { !it.isCompleted && it.progress > 0 }

        DashboardUiState(
            totalItems = total,
            completedItems = completed,
            favoriteItems = favorites,
            inProgressItems = inProgress,
            recentItems = filtered.take(6),
            activeCategoryFilter = category,
            searchQuery = search,
            isLoading = false
        )
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = DashboardUiState(isLoading = true)
    )

    fun onSearchQueryChange(newQuery: String) {
        _searchQuery.value = newQuery
    }

    fun selectCategory(category: ItemCategory?) {
        _selectedCategory.value = if (_selectedCategory.value == category) null else category
    }

    fun toggleCompleted(item: AppItemEntity) {
        viewModelScope.launch {
            repository.toggleCompleted(item)
        }
    }

    fun toggleFavorite(item: AppItemEntity) {
        viewModelScope.launch {
            repository.toggleFavorite(item)
        }
    }

    fun deleteItem(item: AppItemEntity) {
        viewModelScope.launch {
            repository.deleteItem(item)
        }
    }

    fun quickAddItem(title: String, category: ItemCategory = ItemCategory.FEATURE) {
        if (title.isBlank()) return
        viewModelScope.launch {
            repository.insertItem(
                AppItemEntity(
                    title = title,
                    category = category,
                    priority = ItemPriority.MEDIUM,
                    progress = 0
                )
            )
        }
    }

    companion object {
        fun provideFactory(repository: AppItemRepository): ViewModelProvider.Factory =
            object : ViewModelProvider.Factory {
                @Suppress("UNCHECKED_CAST")
                override fun <T : ViewModel> create(modelClass: Class<T>): T {
                    return DashboardViewModel(repository) as T
                }
            }
    }
}
