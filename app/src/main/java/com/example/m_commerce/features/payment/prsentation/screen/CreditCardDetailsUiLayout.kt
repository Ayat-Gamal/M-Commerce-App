package com.example.m_commerce.features.payment.prsentation.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import com.example.m_commerce.core.shared.components.default_top_bar.DefaultTopBar
import com.example.m_commerce.features.payment.prsentation.components.CreditCardDetailsUi

@Composable
fun CreditCardDetailsUiLayout(navController: NavHostController) {
    Scaffold(
        topBar = { DefaultTopBar(title = "Add Card", navController = navController  ) }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)

        ) {
            CreditCardDetailsUi()
        }
    }
}

