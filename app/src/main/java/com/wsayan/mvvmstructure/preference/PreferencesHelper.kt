package com.wsayan.mvvmstructure.preference

import android.content.Context
import android.content.SharedPreferences
import com.google.gson.Gson

class PreferencesHelper(context: Context) {
    private val preferencesHelper: SharedPreferences
    private val prefName = "local-cache"

    init {
        preferencesHelper = context.getSharedPreferences(prefName, Context.MODE_PRIVATE)
    }

    fun put(key: String, value: String?) {
        preferencesHelper.edit().putString(key, value).apply()
    }

    fun put(key: String, value: Int) {
        preferencesHelper.edit().putInt(key, value).apply()
    }

    fun put(key: String, value: Long) {
        preferencesHelper.edit().putLong(key, value).apply()
    }

    fun put(key: String, value: Float) {
        preferencesHelper.edit().putFloat(key, value).apply()
    }

    fun put(key: String, value: Boolean) {
        preferencesHelper.edit().putBoolean(key, value).apply()
    }

    operator fun get(key: String, defaultValue: String): String {
        return preferencesHelper.getString(key, defaultValue) ?: defaultValue
    }

    operator fun get(key: String, defaultValue: Int): Int {
        return preferencesHelper.getInt(key, defaultValue)
    }

    operator fun get(key: String, defaultValue: Float): Float {
        return preferencesHelper.getFloat(key, defaultValue)
    }

    operator fun get(key: String, defaultValue: Boolean): Boolean {
        return preferencesHelper.getBoolean(key, defaultValue)
    }

    operator fun get(key: String, defaultValue: Long): Long {
        return preferencesHelper.getLong(key, defaultValue)
    }

    fun remove(key: String) {
        preferencesHelper.edit().remove(key).apply()
    }

}