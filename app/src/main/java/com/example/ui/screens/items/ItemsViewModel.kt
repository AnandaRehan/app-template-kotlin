package com.example.ui.screens.items

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

enum class ItemSortOption(val label: String) {
    RECENT("Most Recent"),
    PRIORITY("Highest Priority"),
    PROGRESS("Progress"),
    TITLE("Title (A-Z)")
}

data class ItemsUiState(
    val items: List<AppItemEntity> = emptyList(),
    val searchQuery: String = "",
    val selectedCategory: ItemCategory? = null,
    val selectedPriority: ItemPriority? = null,
    val filterFavoritesOnly: Boolean = false,
    val filterCompletedOnly: Boolean = false,
    val sortOption: ItemSortOption = ItemSortOption.RECENT,
    val selectedItemForEdit: AppItemEntity? = null,
    val isAddEditOpen: Boolean = false,
    val isLoading: Boolean = false
)

class ItemsViewModel(
    private val repository: AppItemRepository
) : ViewModel() {

    private val _searchQuery = MutableStateFlow("")
    private val _selectedCategory = MutableStateFlow<ItemCategory?>(null)
    private val _selectedPriority = MutableStateFlow<ItemPriority?>(null)
    private val _filterFavoritesOnly = MutableStateFlow(false)
    private val _filterCompletedOnly = MutableStateFlow(false)
    private val _sortOption = MutableStateFlow(ItemSortOption.RECENT)
    private val _selectedItemForEdit = MutableStateFlow<AppItemEntity?>(null)
    private val _isAddEditOpen = MutableStateFlow(false)

    val uiState: StateFlow<ItemsUiState> = combine(
        repository.getAllItems(),
        _searchQuery,
        _selectedCategory,
        _selectedPriority,
        _filterFavoritesOnly,
        _filterCompletedOnly,
        _sortOption,
        _selectedItemForEdit,
        _isAddEditOpen
    ) { params ->
        @Suppress("UNCHECKED_CAST")
        val rawItems = params[0] as List<AppItemEntity>
        val search = params[1] as String
        val category = params[2] as ItemCategory?
        val priority = params[3] as ItemPriority?
        val favOnly = params[4] as Boolean
        val compOnly = params[5] as Boolean
        val sort = params[6] as ItemSortOption
        val editItem = params[7] as AppItemEntity?
        val isOpen = params[8] as Boolean

        var filtered = rawItems.filter { item ->
            val matchesCategory = category == null || item.category == category
            val matchesPriority = priority == null || item.priority == priority
            val matchesFav = !favOnly || item.isFavorite
            val matchesComp = !compOnly || item.isCompleted
            val matchesSearch = search.isBlank() ||
                item.title.contains(search, ignoreCase = true) ||
                item.description.contains(search, ignoreCase = true) ||
                item.tags.contains(search, ignoreCase = true)

            matchesCategory && matchesPriority && matchesFav && matchesComp && matchesSearch
        }

        filtered = when (sort) {
            ItemSortOption.RECENT -> filtered.sortedByDescending { it.updatedAt }
            ItemSortOption.PRIORITY -> filtered.sortedByDescending { it.priority.level }
            ItemSortOption.PROGRESS -> filtered.sortedByDescending { it.progress }
            ItemSortOption.TITLE -> filtered.sortedBy { it.title.lowercase() }
        }

        ItemsUiState(
            items = filtered,
            searchQuery = search,
            selectedCategory = category,
            selectedPriority = priority,
            filterFavoritesOnly = favOnly,
            filterCompletedOnly = compOnly,
            sortOption = sort,
            selectedItemForEdit = editItem,
            isAddEditOpen = isOpen,
            isLoading = false
        )
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = ItemsUiState(isLoading = true)
    )

    fun onSearchQueryChange(query: String) {
        _searchQuery.value = query
    }

    fun selectCategory(category: ItemCategory?) {
        _selectedCategory.value = if (_selectedCategory.value == category) null else category
    }

    fun selectPriority(priority: ItemPriority?) {
        _selectedPriority.value = if (_selectedPriority.value == priority) null else priority
    }

    fun toggleFavoritesFilter() {
        _filterFavoritesOnly.value = !_filterFavoritesOnly.value
    }

    fun toggleCompletedFilter() {
        _filterCompletedOnly.value = !_filterCompletedOnly.value
    }

    fun setSortOption(option: ItemSortOption) {
        _sortOption.value = option
    }

    fun openAddItem() {
        _selectedItemForEdit.value = null
        _isAddEditOpen.value = true
    }

    fun openEditItem(item: AppItemEntity) {
        _selectedItemForEdit.value = item
        _isAddEditOpen.value = true
    }

    fun closeAddEdit() {
        _selectedItemForEdit.value = null
        _isAddEditOpen.value = false
    }

    fun saveItem(
        title: String,
        description: String,
        category: ItemCategory,
        priority: ItemPriority,
        tags: String,
        progress: Int
    ) {
        if (title.isBlank()) return
        val currentEdit = _selectedItemForEdit.value
        viewModelScope.launch {
            if (currentEdit == null) {
                repository.insertItem(
                    AppItemEntity(
                        title = title.trim(),
                        description = description.trim(),
                        category = category,
                        priority = priority,
                        tags = tags.trim(),
                        progress = progress,
                        isCompleted = progress >= 100
                    )
                )
            } else {
                repository.updateItem(
                    currentEdit.copy(
                        title = title.trim(),
                        description = description.trim(),
                        category = category,
                        priority = priority,
                        tags = tags.trim(),
                        progress = progress,
                        isCompleted = if (progress >= 100) true else currentEdit.isCompleted
                    )
                )
            }
            closeAddEdit()
        }
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

    companion object {
        fun provideFactory(repository: AppItemRepository): ViewModelProvider.Factory =
            object : ViewModelProvider.Factory {
                @Suppress("UNCHECKED_CAST")
                override fun <T : ViewModel> create(modelClass: Class<T>): T {
                    return ItemsViewModel(repository) as T
                }
            }
    }
}
