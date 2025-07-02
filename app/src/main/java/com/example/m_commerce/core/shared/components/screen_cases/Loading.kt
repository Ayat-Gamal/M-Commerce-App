package com.example.m_commerce.core.shared.components.screen_cases

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.m_commerce.R
import com.example.m_commerce.core.shared.components.AnimateLottie

@Composable
fun Loading() {
    Box(
        modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center
    ) {
        AnimateLottie(
            modifier = Modifier.height(150.dp), resourceId = R.raw.loading
        )
    }
}