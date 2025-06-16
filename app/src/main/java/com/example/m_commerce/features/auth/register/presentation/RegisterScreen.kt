package com.example.m_commerce.features.auth.register.presentation

import android.app.Activity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.LocalActivity
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.m_commerce.features.auth.register.presentation.components.RegisterButtonSection
import com.example.m_commerce.features.auth.register.presentation.components.RegisterDividerSection
import com.example.m_commerce.features.auth.shared.presentation.components.AuthFooterSection
import com.example.m_commerce.features.auth.register.presentation.components.RegisterFormSection
import com.example.m_commerce.features.auth.register.presentation.components.RegisterHeaderSection
import com.example.m_commerce.features.auth.shared.presentation.components.AuthSocialSection

@Composable
fun RegisterScreen(navigateToSignIn: () -> Unit) {
    val activity = LocalActivity.current

    BackHandler {
        activity?.finish()
    }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White)
            .padding(horizontal = 8.dp),
        contentAlignment = Alignment.Center
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White)
                .wrapContentHeight()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,

            ) {
            item { RegisterHeaderSection() }
            item { RegisterFormSection() }
            item { RegisterButtonSection() }
            item { RegisterDividerSection() }
            item { AuthSocialSection() }
            item { AuthFooterSection(
                questionText = "Already have an account?",
                actionText = "Sign In",
            ) { navigateToSignIn() }
            }
        }
    }
}

@Preview(showBackground = true, name = "Register Screen Preview", showSystemUi = true)
@Composable
fun RegisterScreenPreview() {
    RegisterScreen {}
}