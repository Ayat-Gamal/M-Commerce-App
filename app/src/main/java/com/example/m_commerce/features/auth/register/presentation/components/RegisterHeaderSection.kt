package com.example.m_commerce.features.auth.register.presentation.components

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun RegisterHeaderSection() {
    Text(
        "Create Account",
        fontSize = 32.sp,
        fontWeight = FontWeight.Medium,
        color = Color.Black
    )
    Spacer(Modifier.height(8.dp))
    Text(
        "Fill your information below or register",
        color = Color.DarkGray,
        fontSize = 14.sp
    )
    Text(
        "with your social account.",
        color = Color.DarkGray,
        fontSize = 14.sp
    )

    Spacer(Modifier.height(32.dp))
}