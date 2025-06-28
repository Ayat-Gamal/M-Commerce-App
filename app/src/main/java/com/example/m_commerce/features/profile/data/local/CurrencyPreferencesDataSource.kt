package com.example.m_commerce.features.profile.data.local

import android.content.SharedPreferences

class CurrencyPreferencesDataSource(
    private val prefs: SharedPreferences
) {
    companion object {
        private const val KEY_EXCHANGE_RATE = "exchange_rate"
        private const val KEY_DEFAULT_CURRENCY = "default_currency_code"
        private const val DEFAULT_RATE = 1.0f
    }

    fun getExchangeRate(): Float =
        prefs.getFloat(KEY_EXCHANGE_RATE, DEFAULT_RATE)

    fun saveExchangeRate(rate: Float) {
        prefs.edit().putFloat(KEY_EXCHANGE_RATE, rate).apply()
    }

    fun getDefaultCurrencyCode(): String? =
        prefs.getString(KEY_DEFAULT_CURRENCY, null)

    fun saveDefaultCurrencyCode(code: String) {
        prefs.edit().putString(KEY_DEFAULT_CURRENCY, code).apply()
    }
}
