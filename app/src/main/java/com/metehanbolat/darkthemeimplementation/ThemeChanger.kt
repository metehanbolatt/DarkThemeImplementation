package com.metehanbolat.darkthemeimplementation

import android.content.Context
import androidx.appcompat.app.AppCompatDelegate
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.map

class ThemeChanger(private val context: Context) {

    companion object {
        private val UI_MODE_KEY = booleanPreferencesKey("ui_mode")
        private val Context.datastore: DataStore<Preferences> by preferencesDataStore(name = "ui_mode_preference")
    }

    suspend fun saveToDataStore(context: Context, isNightMode: Boolean) {
        context.datastore.edit { it[UI_MODE_KEY] = isNightMode }
    }

    val uiMode = context.datastore.data.map { it[UI_MODE_KEY] ?: false }

    suspend fun setUIModel() {
        uiMode.collect {
            if (it) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                saveToDataStore(context, true)
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                saveToDataStore(context, false)
            }
        }
    }

}