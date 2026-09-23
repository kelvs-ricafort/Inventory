package com.kelvsricafort101.wordpress.inventory.ui.settings

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.kelvsricafort101.wordpress.inventory.data.AppLanguage
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private val Context.settingsDataStore by preferencesDataStore(
    name = "settings"
)
class SettingsDataStore(
    private val context: Context
) {
    private companion object {
        val DARK_MODE = booleanPreferencesKey("dark_mode")
        val LANGUAGE = stringPreferencesKey("language")
    }

    val settingsFlow: Flow<SettingsUiState> = context.settingsDataStore.data.map { preferences ->
        val languageCode = preferences[LANGUAGE] ?: AppLanguage.ENGLISH.code

        SettingsUiState(
            darkMode = preferences[DARK_MODE] ?: false,
            appLanguage = AppLanguage.entries.firstOrNull {
                it.code == languageCode
            } ?: AppLanguage.ENGLISH
        )
    }

    suspend fun setDarkMode(enabled: Boolean) {
        context.settingsDataStore.edit { preferences ->
            preferences[DARK_MODE] = enabled
        }
    }

    suspend fun setLanguage(language: AppLanguage) {
        context.settingsDataStore.edit { preferences ->
            preferences[LANGUAGE] = language.code
        }
    }
}