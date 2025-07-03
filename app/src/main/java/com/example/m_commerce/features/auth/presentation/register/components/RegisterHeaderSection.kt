package com.example.m_commerce.features.auth.presentation.register.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.m_commerce.R

@Composable
fun RegisterHeaderSection() {
    Spacer(Modifier.height(12.dp))
    Image(
        painter = painterResource(id = R.drawable.app_logo),
        modifier = Modifier.size(100.dp).clip(shape = CircleShape),
        contentScale = ContentScale.Crop,
        contentDescription = "Login"
    )
    Spacer(Modifier.height(12.dp))

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