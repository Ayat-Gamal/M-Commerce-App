package com.example.m_commerce.features.auth.presentation.login.components

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.m_commerce.config.theme.Teal
import com.example.m_commerce.config.theme.White
import com.example.m_commerce.core.shared.components.CustomButton
import com.example.m_commerce.core.shared.components.CustomHeader
import com.example.m_commerce.core.shared.components.CustomOutlinedTextField
import com.example.m_commerce.features.auth.presentation.components.AuthPasswordTextField
import com.example.m_commerce.features.auth.presentation.login.LoginViewModel

@Composable
fun LoginFormSection(
    isLoading: MutableState<Boolean>,
    viewModel: LoginViewModel = hiltViewModel(),
) {
    val email = remember { mutableStateOf("") }
    val password = remember { mutableStateOf("") }


    CustomHeader(
        "Email",
        Modifier
            .fillMaxWidth()
            .padding(bottom = 4.dp)
    )
    CustomOutlinedTextField(state = email, hint = "example@gmail.com", isLoading = isLoading.value)
    Spacer(Modifier.height(16.dp))

    // Password Section
    CustomHeader(
        "Password", Modifier
            .fillMaxWidth()
            .padding(bottom = 4.dp)
    )

    AuthPasswordTextField(password, "**********", isLoading = isLoading.value)
    Spacer(Modifier.height(24.dp))

    CustomButton(
        text = "Sign In",
        backgroundColor = Teal,
        textColor = White,
        isLoading = isLoading.value,
        onClick = {
            if (!isLoading.value) {
                isLoading.value = true
                viewModel.login(email.value, password.value)
            }
        }
    )
    Spacer(Modifier.height(24.dp))

}