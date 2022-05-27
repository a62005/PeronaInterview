package com.example.peronainterview.database

import android.content.Context
import android.content.SharedPreferences

class SharedPreferencesManager(context: Context) {

    private var sp: SharedPreferences = context.getSharedPreferences("main", Context.MODE_PRIVATE)

    companion object {
        private var instance: SharedPreferencesManager? = null
        fun getInstance(context: Context): SharedPreferencesManager {
            if (null == instance) {
                instance = SharedPreferencesManager(context)
            }
            return instance!!
        }
    }

    fun getLaunchTimes() = sp.getString(KEY_TIMES, null)?.toInt()
    fun setLaunchTimes(times: Int) {
        sp.edit().putString(KEY_TIMES, times.toString()).apply()
    }

}

private const val KEY_TIMES = "times"