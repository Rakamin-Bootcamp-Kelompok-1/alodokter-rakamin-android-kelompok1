package com.example.alodokter_rakamin_android_kelompok1.config

import android.content.Context
import android.content.SharedPreferences
import androidx.datastore.DataStore
import androidx.datastore.preferences.Preferences
import androidx.datastore.preferences.createDataStore
import androidx.datastore.preferences.edit
import androidx.datastore.preferences.preferencesKey

class SharedPreferences(context: Context)  {

    private var prefs: SharedPreferences = context.getSharedPreferences(PREF_NAME, 0)
    private var prefsEditor: SharedPreferences.Editor = prefs.edit()
    private val dataStore: DataStore<Preferences> = context.createDataStore(
        name = "my_data_store"
    )


    fun setFirstTimeLaunch(isFirstTime: Boolean) {
        prefsEditor.putBoolean(IS_FIRST_TIME_LAUNCH, isFirstTime)
        prefsEditor.commit()
    }

    suspend fun saveAuthToken(authToken: String) {
        dataStore.edit { preferences ->
            preferences[KEY_AUTH] = authToken
        }
    }

    fun isFirstTimeLaunch(): Boolean = prefs.getBoolean(IS_FIRST_TIME_LAUNCH, true)

    companion object {
        private const val PREF_NAME = "app-prefs"
        private const val IS_FIRST_TIME_LAUNCH = "IsFirstTimeLaunch"
        private val KEY_AUTH = preferencesKey<String>("key_auth")
    }
}