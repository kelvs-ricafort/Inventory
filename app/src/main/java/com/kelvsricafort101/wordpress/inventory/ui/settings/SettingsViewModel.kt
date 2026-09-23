package com.kelvsricafort101.wordpress.inventory.ui.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.kelvsricafort101.wordpress.inventory.data.AppLanguage
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

data class SettingsUiState(
    val darkMode: Boolean = false,
    val appLanguage: AppLanguage = AppLanguage.ENGLISH
)

class SettingsViewModel(
    private val settingsDataStore: SettingsDataStore
): ViewModel() {
    val uiState: StateFlow<SettingsUiState> =
        settingsDataStore.settingsFlow
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(TIMEOUT_MILLIS),
                initialValue = SettingsUiState()
            )

    fun setDarkMode(enabled: Boolean) {
        viewModelScope.launch {
            settingsDataStore.setDarkMode(enabled)
        }
    }

    fun setLanguage(language: AppLanguage) {
        viewModelScope.launch {
            settingsDataStore.setLanguage(language)
        }
    }
    companion object {
        private const val TIMEOUT_MILLIS = 5_000L
    }
}

class SettingsViewModelFactory(
    private val settingsDataStore: SettingsDataStore
): ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T: ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SettingsViewModel::class.java)) {
            return SettingsViewModel(settingsDataStore = settingsDataStore) as T
        }

        throw IllegalArgumentException("Unknown ViewModel class")
    }
}