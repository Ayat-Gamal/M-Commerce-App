package com.example.m_commerce.features.cart.presentation.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Divider
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.m_commerce.config.theme.Background
import com.example.m_commerce.config.theme.Teal
import com.example.m_commerce.config.theme.dividerGray
import com.example.m_commerce.core.shared.components.default_top_bar.DefaultTopBar
import com.example.m_commerce.features.cart.presentation.components.CartItemCard
import com.example.m_commerce.features.cart.presentation.components.CartReceipt
import com.example.m_commerce.features.orders.presentation.viewmodel.OrderViewModel

@Composable
fun CartScreenUI(paddingValues: PaddingValues, modifier: Modifier = Modifier, viewModel: OrderViewModel = hiltViewModel()) {


    var quantity by rememberSaveable { mutableStateOf(1) }

    Scaffold(
        modifier = modifier.background(Teal),
        topBar = {
            DefaultTopBar(title = "Cart", navController = null, titleCentered = true)
        },
        bottomBar = {
            CartReceipt(paddingValues)
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
                .background(Background)

        ) {
            LazyColumn(
                modifier = Modifier.height(LocalConfiguration.current.screenHeightDp.dp * 0.4f),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(10) { index ->
                    CartItemCard(
                        quantity = quantity,
                        onIncrease = { quantity++ },
                        onDecrease = { if (quantity > 1) quantity-- }
                    )
                    if (index < 9) {
                        Divider(
                            color = dividerGray,
                            thickness = 1.dp,
                            modifier = Modifier.padding(horizontal = 16.dp)
                        )
                    }
                }
            }

        }
    }
}

