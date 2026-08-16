package com.example.data.repository

import com.example.ui.theme.AppColorPalette
import com.example.ui.theme.ThemeMode
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

data class AppPreferences(
    val themeMode: ThemeMode = ThemeMode.SYSTEM,
    val palette: AppColorPalette = AppColorPalette.POLISH,
    val dynamicColor: Boolean = false,
    val showQuickTips: Boolean = true,
    val compactCardView: Boolean = false,
    val developerModeEnabled: Boolean = true
)

class AppPreferencesRepository {
    private val _preferences = MutableStateFlow(AppPreferences())
    val preferences: StateFlow<AppPreferences> = _preferences.asStateFlow()

    fun setThemeMode(mode: ThemeMode) {
        _preferences.update { it.copy(themeMode = mode) }
    }

    fun setColorPalette(palette: AppColorPalette) {
        _preferences.update { it.copy(palette = palette) }
    }

    fun toggleDynamicColor(enabled: Boolean) {
        _preferences.update { it.copy(dynamicColor = enabled) }
    }

    fun toggleQuickTips(enabled: Boolean) {
        _preferences.update { it.copy(showQuickTips = enabled) }
    }

    fun toggleCompactCardView(enabled: Boolean) {
        _preferences.update { it.copy(compactCardView = enabled) }
    }

    fun toggleDeveloperMode(enabled: Boolean) {
        _preferences.update { it.copy(developerModeEnabled = enabled) }
    }
}
