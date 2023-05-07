package com.android.socialchat.datalocal

import android.content.SharedPreferences
import androidx.core.content.edit

class AppPrefs(private val sharedPreferences: SharedPreferences) {
    fun putString(key:String, value:String)=
        sharedPreferences.edit { putString(key,value) }

    fun getString(key:String): String? = sharedPreferences.getString(key,null)

    fun clearData() {
        sharedPreferences.edit{clear()}
    }
}