package com.metehanbolat.darkthemeimplementation

import android.content.Context
import androidx.appcompat.app.AppCompatDelegate
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.map

class ThemeChanger(private val context: Context) {

    companion object {
        private val UI_MODE_KEY = stringPreferencesKey("ui_mode")
        private val Context.datastore: DataStore<Preferences> by preferencesDataStore(name = "ui_mode_preference")
    }

    suspend fun saveToDataStore(isNightMode: String) {
        context.datastore.edit { it[UI_MODE_KEY] = isNightMode }
    }

    val uiMode = context.datastore.data.map { it[UI_MODE_KEY] ?:  ThemeOption.SYSTEM.option}

    suspend fun setUIModel() {
        uiMode.collect {
            when(it) {
                ThemeOption.DARK.option -> {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                    saveToDataStore(ThemeOption.DARK.option)
                }
                ThemeOption.LIGHT.option -> {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                    saveToDataStore(ThemeOption.LIGHT.option)
                }
                ThemeOption.SYSTEM.option -> {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)
                    saveToDataStore(ThemeOption.SYSTEM.option)
                }
            }
        }
    }

    enum class ThemeOption(val option: String) {
        DARK("dark"),
        LIGHT("light"),
        SYSTEM("system")
    }

}