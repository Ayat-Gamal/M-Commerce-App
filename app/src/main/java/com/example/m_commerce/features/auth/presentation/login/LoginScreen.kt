package com.example.m_commerce.features.auth.presentation.login

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.m_commerce.config.routes.AppRoutes
import com.example.m_commerce.features.auth.presentation.components.AuthFooterSection
import com.example.m_commerce.features.auth.presentation.components.AuthSocialSection
import com.example.m_commerce.features.auth.presentation.login.components.LoginDividerSection
import com.example.m_commerce.features.auth.presentation.login.components.LoginFormSection
import com.example.m_commerce.features.auth.presentation.login.components.LoginHeaderSection
import com.example.m_commerce.features.auth.presentation.register.AuthState
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@Composable
fun LoginScreen(
    viewModel: LoginViewModel = hiltViewModel(),
    snackBarHostState: SnackbarHostState,
    navigate: (AppRoutes) -> Unit
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val scope = rememberCoroutineScope()
    val isLoading = remember { mutableStateOf(false) }

    when (uiState) {
        is AuthState.Error -> {
            isLoading.value = false
            LaunchedEffect(Unit) {
                snackBarHostState.currentSnackbarData?.dismiss()
                scope.launch {
                    snackBarHostState.showSnackbar((uiState as AuthState.Error).message)
                }
            }
        }

        is AuthState.Idle -> {
            isLoading.value = false
        }

        is AuthState.Loading -> {
            isLoading.value = true
        }

        is AuthState.Success -> {
            isLoading.value = true
            LaunchedEffect(Unit) {
                snackBarHostState.currentSnackbarData?.dismiss()
                scope.launch {
                    snackBarHostState.showSnackbar("Logged In Successful")
                    withContext(Dispatchers.Main) {
                        navigate(AppRoutes.HomeScreen)
                    }
                }

            }
        }
    }

    LaunchedEffect(Unit) {
        FirebaseAuth.getInstance().signOut()
        viewModel.messageState.collect {
            isLoading.value = false
            if (it.isNotBlank()) {
                snackBarHostState.currentSnackbarData?.dismiss()
                scope.launch {
                    snackBarHostState.showSnackbar(it)
                }
            }
        }
    }

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

            item { LoginFormSection(isLoading = isLoading, navigate = navigate) }
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