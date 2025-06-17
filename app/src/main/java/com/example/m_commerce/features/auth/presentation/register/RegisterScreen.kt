package com.example.m_commerce.features.auth.presentation.register

import android.util.Log
import androidx.activity.compose.BackHandler
import androidx.activity.compose.LocalActivity
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.m_commerce.features.auth.presentation.register.components.RegisterDividerSection
import com.example.m_commerce.features.auth.presentation.register.components.RegisterFormSection
import com.example.m_commerce.features.auth.presentation.register.components.RegisterHeaderSection
import com.example.m_commerce.features.auth.presentation.components.AuthFooterSection
import com.example.m_commerce.features.auth.presentation.components.AuthSocialSection
import kotlinx.coroutines.launch

@Composable
fun RegisterScreen(
    snackBarHostState: SnackbarHostState,
    viewModel: RegisterViewModel = hiltViewModel(),
    navigateToSignIn: () -> Unit
) {
    val activity = LocalActivity.current
    val state by viewModel.registerState.collectAsState()
    val context = LocalContext.current
    val scope = rememberCoroutineScope()

    when (state) {
        is RegisterState.Error -> {
            LaunchedEffect(Unit) {
                snackBarHostState.currentSnackbarData?.dismiss()
                scope.launch {
                    snackBarHostState.showSnackbar((state as RegisterState.Error).message)
                }
            }
        }

        is RegisterState.Idle -> {}
        is RegisterState.Loading -> {
            Log.d("TAG", "RegisterScreen: RegisterState.Loading")

        }

        is RegisterState.Success -> {
            LaunchedEffect(Unit) {
                snackBarHostState.currentSnackbarData?.dismiss()
                scope.launch {
                    val result = snackBarHostState.showSnackbar(
                        message = "Check your email for verification",
                    )
                    if (result == SnackbarResult.Dismissed) navigateToSignIn()
                }

            }
        }
    }

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
            item { RegisterDividerSection() }
            item { AuthSocialSection() }
            item {
                AuthFooterSection(
                    questionText = "Already have an account?",
                    actionText = "Sign In",
                ) { navigateToSignIn() }
            }
        }
    }
}