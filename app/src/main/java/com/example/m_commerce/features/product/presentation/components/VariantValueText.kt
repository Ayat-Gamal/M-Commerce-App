package com.example.m_commerce.features.product.presentation.components

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun VariantValueText(selectedSize: String?) {
    Text(
        selectedSize?.uppercase() ?: "",
        modifier = Modifier.padding(start = 4.dp),
        fontSize = 18.sp,
        color = Color.Black,
        fontWeight = FontWeight.Bold
    )
}