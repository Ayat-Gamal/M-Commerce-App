package com.example.m_commerce.core.shared.components.default_top_bar

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController

@Composable
fun BackButton(navController: NavController, modifier: Modifier = Modifier) {
    IconButton(
        modifier = modifier,
        onClick = { navController.popBackStack() }
    ) {
        Icon(
            imageVector = Icons.Default.ArrowBack,
            contentDescription = "Back"
        )
    }
}