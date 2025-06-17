package com.example.m_commerce.features.auth.presentation.login

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.m_commerce.config.routes.AppRoutes
import com.example.m_commerce.features.auth.presentation.login.components.LoginDividerSection
import com.example.m_commerce.features.auth.presentation.login.components.LoginFormSection
import com.example.m_commerce.features.auth.presentation.login.components.LoginHeaderSection
import com.example.m_commerce.features.auth.presentation.components.AuthFooterSection
import com.example.m_commerce.features.auth.presentation.components.AuthSocialSection

@Composable
fun LoginScreen(navigate: (AppRoutes) -> Unit) {

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .padding(horizontal = 8.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            item { LoginHeaderSection() }

            item { LoginFormSection(navigate) }
            item { LoginDividerSection() }
            item { AuthSocialSection() }
            item {
                AuthFooterSection(
                    questionText = "Don't have an account?",
                    actionText = "Sign Up"
                ) { navigate(AppRoutes.RegisterScreen) }
            }

            item {
                AuthFooterSection(
                    questionText = "Or, Login as a",
                    actionText = "Guest"
                ) { navigate(AppRoutes.HomeScreen) }
            }

        }
    }
}

@Preview(showBackground = true, name = "Register Screen Preview", showSystemUi = true)
@Composable
fun LoginScreenPreview() {
    LoginScreen {}
}