package com.example.m_commerce.features.auth.presentation.login.components

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.m_commerce.config.routes.AppRoutes
import com.example.m_commerce.config.theme.Teal
import com.example.m_commerce.config.theme.White
import com.example.m_commerce.core.shared.components.CustomButton
import com.example.m_commerce.core.shared.components.CustomHeader
import com.example.m_commerce.core.shared.components.CustomOutlinedTextField
import com.example.m_commerce.features.auth.presentation.components.AuthPasswordTextField

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
        backgroundColor = Teal,
        textColor = White,
        onClick = { navigate(AppRoutes.HomeScreen) }
    )
    Spacer(Modifier.height(24.dp))

}