package com.example.alodokter_rakamin_android_kelompok1.config

import android.content.Context
import android.content.SharedPreferences

class SharedPreferences(private val context: Context)  {

    private var prefs: SharedPreferences = context.getSharedPreferences(PREF_NAME, 0)
    private var prefsEditor: SharedPreferences.Editor = prefs.edit()

    fun setFirstTimeLaunch(isFirstTime: Boolean) {
        prefsEditor.putBoolean(IS_FIRST_TIME_LAUNCH, isFirstTime)
        prefsEditor.commit()
    }

    fun isFirstTimeLaunch(): Boolean = prefs.getBoolean(IS_FIRST_TIME_LAUNCH, true)

    companion object {
        private const val PREF_NAME = "app-prefs"
        private const val IS_FIRST_TIME_LAUNCH = "IsFirstTimeLaunch"
    }
}