package com.example.m_commerce.features.auth.register.presentation.components

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import com.example.m_commerce.core.shared.components.CustomHeader
import com.example.m_commerce.core.shared.components.CustomOutlinedTextField
import com.example.m_commerce.features.auth.shared.presentation.components.AuthPasswordTextField

@Composable
fun RegisterFormSection() {
    val name = remember { mutableStateOf("") }
    val email = remember { mutableStateOf("") }
    val password = remember { mutableStateOf("") }
    val confirmPassword = remember { mutableStateOf("") }

    // Name Field
    CustomHeader("Name", Modifier.fillMaxWidth().padding(bottom = 4.dp))
    CustomOutlinedTextField(state = name, hint = "Ex. Husain Namir")
    Spacer(Modifier.height(16.dp))

    // Email Field
    CustomHeader("Email", Modifier.fillMaxWidth().padding(bottom = 4.dp))
    CustomOutlinedTextField(state = email, hint = "example@gmail.com")
    Spacer(Modifier.height(16.dp))

    // Password Field
    CustomHeader("Password", Modifier.fillMaxWidth().padding(bottom = 4.dp))
    AuthPasswordTextField(
        state = password,
        hint = "**********",
    )
    Spacer(Modifier.height(16.dp))

    // Confirm Password Field
    CustomHeader("Confirm Password", Modifier.fillMaxWidth().padding(bottom = 4.dp))

    AuthPasswordTextField(
        state = confirmPassword,
        hint = "**********",
    )
    Spacer(Modifier.height(24.dp))
}