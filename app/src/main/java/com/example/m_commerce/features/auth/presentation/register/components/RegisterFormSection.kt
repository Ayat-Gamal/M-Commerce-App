package com.example.m_commerce.features.auth.presentation.register.components

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.m_commerce.core.shared.components.CustomButton
import com.example.m_commerce.core.shared.components.CustomHeader
import com.example.m_commerce.core.shared.components.CustomOutlinedTextField
import com.example.m_commerce.features.auth.presentation.register.RegisterViewModel
import com.example.m_commerce.features.auth.presentation.components.AuthPasswordTextField

@Composable
fun RegisterFormSection(viewModel: RegisterViewModel = hiltViewModel()) {
    val name = remember { mutableStateOf("") }
    val email = remember { mutableStateOf("") }
    val password = remember { mutableStateOf("") }
    val confirmPassword = remember { mutableStateOf("") }

    // Name Field
    CustomHeader(
        "Name", Modifier
            .fillMaxWidth()
            .padding(bottom = 4.dp)
    )
    CustomOutlinedTextField(state = name, hint = "Ex. Husain Namir")
    Spacer(Modifier.height(16.dp))

    // Email Field
    CustomHeader(
        "Email", Modifier
            .fillMaxWidth()
            .padding(bottom = 4.dp)
    )
    CustomOutlinedTextField(state = email, hint = "example@gmail.com")
    Spacer(Modifier.height(16.dp))

    // Password Field
    CustomHeader(
        "Password", Modifier
            .fillMaxWidth()
            .padding(bottom = 4.dp)
    )
    AuthPasswordTextField(
        state = password,
        hint = "**********",
    )
    Spacer(Modifier.height(16.dp))

    // Confirm Password Field
    CustomHeader(
        "Confirm Password", Modifier
            .fillMaxWidth()
            .padding(bottom = 4.dp)
    )

    AuthPasswordTextField(
        state = confirmPassword,
        hint = "**********",
    )
    Spacer(Modifier.height(24.dp))

    CustomButton(text = "Sign Up",
        backgroundColor = Color(0xFF008B86),
        textColor = Color.White,
        onClick = {
            viewModel.register("medosaad2071@gmail.com", "123456")
        })
    Spacer(Modifier.height(24.dp))
}
