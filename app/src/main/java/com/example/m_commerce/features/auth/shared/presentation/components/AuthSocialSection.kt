package com.example.m_commerce.features.auth.shared.presentation.components

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.example.m_commerce.R

@Composable
fun AuthSocialSection() {
    Button(
        onClick = {},
        modifier = Modifier
            .size(56.dp)
            .border(1.dp, Color.LightGray, CircleShape),
        shape = CircleShape,
        colors = ButtonDefaults.buttonColors(containerColor = Color.White),
        contentPadding = PaddingValues(0.dp)
    ) {
        AsyncImage(
            model = R.drawable.ic_google,
            contentDescription = "Google Logo",
            modifier = Modifier.size(32.dp)
        )
    }
    Spacer(Modifier.height(24.dp))
}