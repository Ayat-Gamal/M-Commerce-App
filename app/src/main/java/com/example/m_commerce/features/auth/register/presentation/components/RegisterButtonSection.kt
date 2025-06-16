package com.example.m_commerce.features.auth.register.presentation.components

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.m_commerce.core.shared.components.CustomButton

@Composable
fun RegisterButtonSection() {
    CustomButton(text = "Sign Up", backgroundColor = Color(0xFF008B86), textColor = Color.White)
    Spacer(Modifier.height(24.dp))
}