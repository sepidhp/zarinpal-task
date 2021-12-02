package com.zarinpal.utils

import android.content.Context
import android.content.SharedPreferences
import androidx.preference.PreferenceManager
import javax.inject.Inject

class SharedPreferencesHelper @Inject constructor(
    context: Context,
    sharedPreferencesName: String? = null
) {

    private var sharedPreferences: SharedPreferences = if (sharedPreferencesName != null)
        context.getSharedPreferences(sharedPreferencesName, Context.MODE_PRIVATE)
    else
        PreferenceManager.getDefaultSharedPreferences(context)

    fun getInt(key: String, defaultValue: Int) = sharedPreferences.getInt(key, defaultValue)

    fun getBoolean(key: String, defaultValue: Boolean) =
        sharedPreferences.getBoolean(key, defaultValue)

    fun getString(key: String, defaultValue: String?) =
        sharedPreferences.getString(key, defaultValue)

    fun getLong(key: String, defaultValue: Long) = sharedPreferences.getLong(key, defaultValue)

    fun putInt(key: String, value: Int) = sharedPreferences.edit().putInt(key, value).commit()

    fun putBoolean(key: String, value: Boolean) =
        sharedPreferences.edit().putBoolean(key, value).commit()

    fun putString(key: String, value: String?) =
        sharedPreferences.edit().putString(key, value).commit()

    fun putLong(key: String, value: Long) = sharedPreferences.edit().putLong(key, value).commit()

    fun clear() = sharedPreferences.edit().clear().commit()
}