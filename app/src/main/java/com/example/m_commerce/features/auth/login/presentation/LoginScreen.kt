package com.example.m_commerce.features.auth.login.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.m_commerce.config.routes.AppRoutes
import com.example.m_commerce.features.auth.login.presentation.components.LoginDividerSection
import com.example.m_commerce.features.auth.login.presentation.components.LoginFormSection
import com.example.m_commerce.features.auth.login.presentation.components.LoginHeaderSection
import com.example.m_commerce.features.auth.shared.presentation.components.AuthFooterSection
import com.example.m_commerce.features.auth.shared.presentation.components.AuthSocialSection

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

        }
    }
}

@Preview(showBackground = true, name = "Register Screen Preview", showSystemUi = true)
@Composable
fun LoginScreenPreview() {
    LoginScreen {}
}