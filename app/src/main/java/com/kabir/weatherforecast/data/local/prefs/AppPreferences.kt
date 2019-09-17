package com.kabir.weatherforecast.data.local.prefs

import android.content.Context
import android.content.SharedPreferences

class AppPreferences(context: Context) {

    private val prefs: SharedPreferences = context.getSharedPreferences(
        "app_preferences", Context.MODE_PRIVATE
    )

    companion object {
        const val KEY_API_KEY = "api_key"
        const val KEY_LAST_SUCCESSFUL_INPUT = "last_successful_input"
    }

    fun clear() = prefs.edit().clear().commit()

    fun getApiKey(): String? = prefs.getString(KEY_API_KEY, null)

    fun setApiKey(value: String) =
        prefs.edit().putString(KEY_API_KEY, value).commit()

    fun getLastSuccessfulInput(): String? = prefs.getString(KEY_LAST_SUCCESSFUL_INPUT, null)

    fun setLastSuccessfulInput(value: String) =
        prefs.edit().putString(KEY_LAST_SUCCESSFUL_INPUT, value).commit()
}