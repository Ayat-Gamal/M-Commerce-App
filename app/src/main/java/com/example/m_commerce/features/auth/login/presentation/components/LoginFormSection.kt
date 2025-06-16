package com.example.m_commerce.features.auth.login.presentation.components

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.m_commerce.config.routes.AppRoutes
import com.example.m_commerce.core.shared.components.CustomButton
import com.example.m_commerce.core.shared.components.CustomHeader
import com.example.m_commerce.core.shared.components.CustomOutlinedTextField
import com.example.m_commerce.features.auth.shared.presentation.components.AuthPasswordTextField

@Composable
fun LoginFormSection(
    navigate: (AppRoutes) -> Unit
) {
    val email = remember { mutableStateOf("") }
    val password = remember { mutableStateOf("") }

    CustomHeader(
        "Email",
        Modifier
            .fillMaxWidth()
            .padding(bottom = 4.dp)
    )
    CustomOutlinedTextField(state = email, hint = "example@gmail.com")
    Spacer(Modifier.height(16.dp))

    // Password Section
    CustomHeader(
        "Password", Modifier
            .fillMaxWidth()
            .padding(bottom = 4.dp)
    )

    AuthPasswordTextField(password, "**********")
    Spacer(Modifier.height(24.dp))

    CustomButton(
        text = "Sign In",
        backgroundColor = Color(0xFF008B86),
        textColor = Color.White,
        onClick = { navigate(AppRoutes.HomeScreen) }
    )
    Spacer(Modifier.height(24.dp))

}