package com.example.m_commerce.features.auth.domain.validation

data class ValidationResult(
    val successful: Boolean,
    val errorMessage: String? = null
)
