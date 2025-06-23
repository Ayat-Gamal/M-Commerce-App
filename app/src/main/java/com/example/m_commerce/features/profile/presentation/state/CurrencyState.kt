package com.example.m_commerce.features.profile.presentation.state

import com.example.m_commerce.features.profile.domain.model.CurrencyDetails

data class CurrencyState(
    val currencies: List<CurrencyDetails> = emptyList(),
    val isLoading: Boolean = false,
    val error: String = ""
)
