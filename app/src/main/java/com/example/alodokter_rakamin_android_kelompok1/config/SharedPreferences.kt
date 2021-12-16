package com.example.alodokter_rakamin_android_kelompok1.config

import android.content.Context
import android.content.SharedPreferences

class SharedPreferences(context: Context)  {

    private var prefs: SharedPreferences = context.getSharedPreferences(PREF_NAME, 0)
    private var prefsEditor: SharedPreferences.Editor = prefs.edit()

    fun setFirstTimeLaunch(isFirstTime: Boolean) {
        prefsEditor.putBoolean(IS_FIRST_TIME_LAUNCH, isFirstTime)
        prefsEditor.commit()
    }

    fun setUser(token: String,isLogin: Boolean){
        prefsEditor.putString(USER_TOKEN,token)
        prefsEditor.putBoolean(IS_LOGIN_STATUS,isLogin)
        prefsEditor.commit()
    }

    fun isFirstTimeLaunch(): Boolean = prefs.getBoolean(IS_FIRST_TIME_LAUNCH, true)

    fun getLoggedStatus() : Boolean = prefs.getBoolean(IS_LOGIN_STATUS,false)

    fun getUserToken() : String? = prefs.getString(USER_TOKEN,"")

    fun setLogout(){
        prefsEditor.clear()
        setFirstTimeLaunch(false)
    }

    companion object {
        private const val PREF_NAME = "app-prefs"
        private const val IS_FIRST_TIME_LAUNCH = "IsFirstTimeLaunch"
        private const val IS_LOGIN_STATUS = "is_logging_status"
        private const val USER_TOKEN = "user_token"
    }
}