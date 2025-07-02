package com.example.m_commerce.core.shared.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import com.example.m_commerce.R

@Composable
 fun BlockingLoadingOverlay(isLoading: Boolean) {
    if (isLoading) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White.copy(alpha = 0.3f))
                .clickable(enabled = true, onClick = {})  // block clicks
                .zIndex(1f),
            contentAlignment = Alignment.Center
        ) {
            AnimateLottie(
                modifier = Modifier.size(250.dp),
                resourceId = R.raw.overlay_loading
            )
        }
    }
}