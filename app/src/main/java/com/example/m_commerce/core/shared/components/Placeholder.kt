package com.example.m_commerce.core.shared.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun Placeholder(
    modifier: Modifier = Modifier,
    color: Color = Color.Gray,
    strokeWidth: Dp = 2.dp
) {
    Box(
        modifier = modifier
            .background(color.copy(alpha = 0.1f))
            .border(strokeWidth, color)
    )
}
