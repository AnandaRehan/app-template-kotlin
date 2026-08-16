package com.example.ui.screens.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.data.local.AppDatabase
import com.example.data.repository.AppItemRepository
import com.example.data.repository.AppPreferences
import com.example.data.repository.AppPreferencesRepository
import com.example.ui.theme.AppColorPalette
import com.example.ui.theme.ThemeMode
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

data class SettingsUiState(
    val preferences: AppPreferences = AppPreferences(),
    val totalItemsCount: Int = 0,
    val completedItemsCount: Int = 0,
    val favoriteItemsCount: Int = 0
)

class SettingsViewModel(
    private val preferencesRepository: AppPreferencesRepository,
    private val itemRepository: AppItemRepository
) : ViewModel() {

    val uiState: StateFlow<SettingsUiState> = combine(
        preferencesRepository.preferences,
        itemRepository.getItemCount(),
        itemRepository.getCompletedCount(),
        itemRepository.getFavoriteCount()
    ) { prefs, total, completed, favs ->
        SettingsUiState(
            preferences = prefs,
            totalItemsCount = total,
            completedItemsCount = completed,
            favoriteItemsCount = favs
        )
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = SettingsUiState()
    )

    fun setThemeMode(mode: ThemeMode) {
        preferencesRepository.setThemeMode(mode)
    }

    fun setColorPalette(palette: AppColorPalette) {
        preferencesRepository.setColorPalette(palette)
    }

    fun toggleDynamicColor(enabled: Boolean) {
        preferencesRepository.toggleDynamicColor(enabled)
    }

    fun toggleQuickTips(enabled: Boolean) {
        preferencesRepository.toggleQuickTips(enabled)
    }

    fun toggleDeveloperMode(enabled: Boolean) {
        preferencesRepository.toggleDeveloperMode(enabled)
    }

    fun resetDemoData() {
        viewModelScope.launch {
            itemRepository.deleteAll()
            itemRepository.insertItems(AppDatabase.initialDemoItems)
        }
    }

    fun clearAllData() {
        viewModelScope.launch {
            itemRepository.deleteAll()
        }
    }

    companion object {
        fun provideFactory(
            preferencesRepository: AppPreferencesRepository,
            itemRepository: AppItemRepository
        ): ViewModelProvider.Factory =
            object : ViewModelProvider.Factory {
                @Suppress("UNCHECKED_CAST")
                override fun <T : ViewModel> create(modelClass: Class<T>): T {
                    return SettingsViewModel(preferencesRepository, itemRepository) as T
                }
            }
    }
}
