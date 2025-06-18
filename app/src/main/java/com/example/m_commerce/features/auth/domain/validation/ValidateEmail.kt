package com.example.m_commerce.features.auth.domain.validation

import android.util.Patterns

class ValidateEmail {
    operator fun invoke(email: String): ValidationResult {
        if (email.isBlank()) return ValidationResult(false, "Email cannot be empty")
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            return ValidationResult(false, "Invalid email address")
        }
        return ValidationResult(true)
    }
}