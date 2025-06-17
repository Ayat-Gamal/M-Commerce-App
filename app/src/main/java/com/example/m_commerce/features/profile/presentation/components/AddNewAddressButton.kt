package com.example.m_commerce.features.profile.presentation.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.TileMode
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.m_commerce.config.fonts.InterFontFamily
import com.example.m_commerce.config.theme.Teal


@Composable
fun AddNewAddressButton(onClick: () -> Unit) {
    Surface(
        shape = RoundedCornerShape(12.dp),
        border = BorderStroke(
            width = 1.dp,
            brush = Brush.horizontalGradient(
                colors = listOf(Teal, Teal),
                startX = 0f,
                endX = Float.POSITIVE_INFINITY,
                tileMode = TileMode.Repeated
            )
        ),
        color = Color.White,
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .clickable { onClick() }
    ) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier.padding(16.dp)

        ) {
            Text(
                text = "+ Add New Shipping Address",
                color = Teal,
                fontWeight = FontWeight.Medium
            )
        }
    }

}
