package com.example.m_commerce.features.profile.data.mapper

import com.example.m_commerce.features.profile.domain.entity.ApiConversionRateResponse
import com.example.m_commerce.features.profile.domain.entity.ApiSymbolResponse
import com.example.m_commerce.features.profile.domain.entity.LatestRatesResponse
import com.example.m_commerce.features.profile.domain.entity.SymbolResponse

fun ApiSymbolResponse.toSymbolResponse(): SymbolResponse {
    val symbolMap = supported_codes.associate { (code, name) ->
        code to name
    }
    return SymbolResponse(
        success = result == "success",
        symbols = symbolMap
    )
}

fun ApiConversionRateResponse.toLatestRatesResponse(): LatestRatesResponse {
    return LatestRatesResponse(
        success = result == "success",
        base = base_code,
        rates = mapOf(target_code to conversion_rate)
    )
}
