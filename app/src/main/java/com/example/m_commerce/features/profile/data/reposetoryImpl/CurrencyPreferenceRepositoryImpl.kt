package com.example.m_commerce.features.profile.data.reposetoryImpl

import android.content.SharedPreferences
import com.example.m_commerce.features.profile.domain.model.CurrencyDetails
import com.example.m_commerce.features.profile.domain.repository.CurrencyPreferencesRepository
import com.google.gson.Gson
import javax.inject.Inject

class CurrencyPreferencesRepositoryImpl @Inject constructor(
    private val sharedPreferences: SharedPreferences,
    private val gson: Gson
) : CurrencyPreferencesRepository {

    companion object {
        private const val KEY_CURRENCY_DETAILS = "currency_details"
    }

    override suspend fun saveCurrencyDetails(details: CurrencyDetails) {
        val json = gson.toJson(details)
        sharedPreferences.edit().putString(KEY_CURRENCY_DETAILS, json).apply()
    }

    override suspend fun getCurrencyDetails(): CurrencyDetails? {
        val json = sharedPreferences.getString(KEY_CURRENCY_DETAILS, null) ?: return null
        return gson.fromJson(json, CurrencyDetails::class.java)
    }

}
