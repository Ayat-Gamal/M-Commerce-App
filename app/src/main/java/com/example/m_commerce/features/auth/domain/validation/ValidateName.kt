package com.example.m_commerce.features.auth.domain.validation

class ValidateName {
    operator fun invoke(name: String): ValidationResult {
        if (name.isBlank()) return ValidationResult(false, "Name cannot be empty")
        if (name.length < 2) return ValidationResult(false, "Name too short")
        return ValidationResult(true)
    }
}