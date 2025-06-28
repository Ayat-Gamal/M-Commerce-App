package com.example.m_commerce.features.product.presentation.components

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

@Composable
fun VariantHeaderText(variant: String) {
    Text(
        "$variant:",
        fontSize = 18.sp,
        color = Color.Black.copy(0.8f),
        fontWeight = FontWeight.Bold
    )
}