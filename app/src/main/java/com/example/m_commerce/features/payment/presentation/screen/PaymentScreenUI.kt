package com.example.m_commerce.features.payment.presentation.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.m_commerce.core.shared.components.Placeholder
import com.example.m_commerce.core.shared.components.default_top_bar.DefaultTopBar


@Composable
fun PaymentScreenUI(navController: NavHostController, modifier: Modifier = Modifier) {

    Scaffold(modifier = modifier, topBar = { DefaultTopBar(title = "Payment Method", navController = navController) }) { padding ->

        Column(modifier = Modifier.padding(padding)) {
            Placeholder(modifier = Modifier.weight(1f).height(200.dp))
        }

    }
}