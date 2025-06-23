package com.example.m_commerce.features.profile.presentation.state

import com.example.m_commerce.features.profile.domain.model.CurrencyDetails

class DefaultCurrencyState(
    var defaultCurrency : CurrencyDetails?,
    val isLoading: Boolean = false,
    val error: String = ""
)

