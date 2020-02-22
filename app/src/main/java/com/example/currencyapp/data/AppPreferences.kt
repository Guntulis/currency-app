package com.example.currencyapp.data

import android.content.Context
import android.content.SharedPreferences

class AppPreferences(context: Context) {
    private val prefs: SharedPreferences = context.getSharedPreferences(PREFS_FILENAME, 0)

    var baseCurrency: String?
        get() = prefs.getString(BASE_CURRENCY_KEY, "")!!
        set(value) = prefs.edit().putString(BASE_CURRENCY_KEY, value).apply()

    companion object {
        private const val PREFS_FILENAME = "com.example.currencyapp.prefs"
        private const val BASE_CURRENCY_KEY = "base_currency"
    }
}