package com.example.m_commerce.features.auth.domain.validation

class ValidateConfirmPassword {
    operator fun invoke(password: String, confirmPassword: String): ValidationResult {
        if (password != confirmPassword) return ValidationResult(false, "Passwords do not match")
        return ValidationResult(true)
    }
}