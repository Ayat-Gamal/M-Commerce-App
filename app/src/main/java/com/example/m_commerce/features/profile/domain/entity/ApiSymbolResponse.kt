package com.example.m_commerce.features.profile.domain.entity

data class ApiSymbolResponse(
    val result: String,
    val supported_codes: List<List<String>>
)
