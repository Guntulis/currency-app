package com.example.currencyapp.data

import android.content.Context
import android.content.SharedPreferences

class AppPreferences(context: Context) {
    private val prefs: SharedPreferences = context.getSharedPreferences(PREFS_FILENAME, 0)

    var ratesResponse: String?
        get() = prefs.getString(RATES_RESPONSE_KEY, "")!!
        set(value) = prefs.edit().putString(RATES_RESPONSE_KEY, value).apply()

    companion object {
        private const val PREFS_FILENAME = "com.example.currencyapp.prefs"
        private const val RATES_RESPONSE_KEY = "rates_response"
    }
}