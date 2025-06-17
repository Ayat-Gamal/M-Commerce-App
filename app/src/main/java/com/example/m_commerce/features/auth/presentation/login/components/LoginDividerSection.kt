package com.example.m_commerce.features.auth.presentation.login.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun LoginDividerSection() {
    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {
        HorizontalDivider(Modifier.weight(1f))
        Text(
            "  Or sign in with  ",
            color = Color(0xFF797979),
            fontSize = 12.sp
        )
        HorizontalDivider(Modifier.weight(1f))
    }
    Spacer(Modifier.height(16.dp))
}