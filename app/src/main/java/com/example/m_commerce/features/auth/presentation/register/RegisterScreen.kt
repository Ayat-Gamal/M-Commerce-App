package com.example.m_commerce.features.auth.presentation.register

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.SnackbarDuration
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
import com.example.m_commerce.features.auth.presentation.components.AuthFooterSection
import com.example.m_commerce.features.auth.presentation.components.AuthSocialSection
import com.example.m_commerce.features.auth.presentation.register.components.RegisterDividerSection
import com.example.m_commerce.features.auth.presentation.register.components.RegisterFormSection
import com.example.m_commerce.features.auth.presentation.register.components.RegisterHeaderSection
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@Composable
fun RegisterScreen(
    snackBarHostState: SnackbarHostState,
    viewModel: RegisterViewModel = hiltViewModel(),
    navigateToSignIn: () -> Unit
) {
    val state by viewModel.registerState.collectAsStateWithLifecycle()
    val isLoading = remember { mutableStateOf(false) }
    val scope = rememberCoroutineScope()

    when (state) {
        is AuthState.Error -> {
            isLoading.value = false
            LaunchedEffect(Unit) {
                snackBarHostState.currentSnackbarData?.dismiss()
                scope.launch {
                    snackBarHostState.showSnackbar((state as AuthState.Error).message)
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
                val user = FirebaseAuth.getInstance().currentUser
                snackBarHostState.currentSnackbarData?.dismiss()
                scope.launch {
                    snackBarHostState.showSnackbar(
                        message = "Check your email for verification",
                        duration = SnackbarDuration.Short
                    )
                    withContext(Dispatchers.Main) {
                        navigateToSignIn()
                    }
                }
            }
        }
    }

    LaunchedEffect(Unit) {
        viewModel.messageState.collect {
            if (it.isNotBlank()) {
                isLoading.value = false
                snackBarHostState.currentSnackbarData?.dismiss()
                scope.launch {
                    snackBarHostState.showSnackbar(it)
                }
            }
        }
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
            item { RegisterFormSection(isLoading = isLoading) }
            item { RegisterDividerSection() }
            item { AuthSocialSection(viewModel) }
            item {
                AuthFooterSection(
                    questionText = "Already have an account?",
                    actionText = "Sign In",
                ) { navigateToSignIn() }
            }
        }
    }
}