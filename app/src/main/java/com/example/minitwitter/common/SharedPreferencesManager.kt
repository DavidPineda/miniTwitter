package com.example.minitwitter.common

import android.content.Context
import android.content.SharedPreferences

class SharedPreferencesManager {

    companion object {
        const val APP_SETTINGS_FILE: String = "APP_SETTINGS"

        private fun getSharedPreferences(): SharedPreferences {
            return MyApp.getContext().getSharedPreferences(APP_SETTINGS_FILE, Context.MODE_PRIVATE)
        }

        fun setSomeStringValue(key: String, value: String) {
            val sharedPref = getSharedPreferences()
            with (sharedPref.edit()) {
                putString(key, value)
                commit()
            }
        }

        fun setSomeBooleanValue(key: String, value: Boolean) {
            val sharedPref = getSharedPreferences()
            with (sharedPref.edit()) {
                putBoolean(key, value)
                commit()
            }
        }

        fun getSomeStringValue(key: String): String {
            return getSharedPreferences().getString(key, null).toString()
        }

        fun getSomeBooleanValue(key: String): Boolean {
            return getSharedPreferences().getBoolean(key, false)
        }
    }
}