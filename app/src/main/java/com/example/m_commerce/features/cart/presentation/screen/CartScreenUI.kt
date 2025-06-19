package com.example.m_commerce.features.cart.presentation.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Divider
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.ui.input.nestedscroll.NestedScrollSource
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.m_commerce.config.theme.Background
import com.example.m_commerce.config.theme.Teal
import com.example.m_commerce.config.theme.dividerGray
import com.example.m_commerce.core.shared.components.default_top_bar.DefaultTopBar
import com.example.m_commerce.features.cart.presentation.components.CartItemCard
import com.example.m_commerce.features.cart.presentation.components.CartReceipt
import com.example.m_commerce.features.cart.presentation.components.PromoCodeInput

@Composable
fun CartScreenUI(modifier: Modifier = Modifier) {
    var quantity by rememberSaveable { mutableStateOf(1) }

    Scaffold(
        modifier = modifier.background(Teal),
        topBar = {
            DefaultTopBar(title = "Cart", navController = null, titleCentered = true)
        }
    ) { padding ->

        val mainScrollState = rememberScrollState()
        val nestedScrollConnection = remember {
            object : NestedScrollConnection {
                override fun onPreScroll(available: Offset, source: NestedScrollSource): Offset {
                    return Offset.Zero
                }
            }
        }

        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize().background(Background)
                .verticalScroll(mainScrollState)
                .nestedScroll(nestedScrollConnection)
        ) {
            LazyColumn(
                modifier = Modifier.height( LocalConfiguration.current.screenHeightDp.dp * 0.4f)
                    .nestedScroll(nestedScrollConnection),
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

            Divider(
                thickness = 1.dp,
                modifier = Modifier.padding(16.dp)
            )

            PromoCodeInput(
                promoCode = "",
                onPromoCodeChange = {},
                onApplyClick = {},
                modifier = Modifier.padding(16.dp)
            )
            CartReceipt()
            Spacer(modifier = Modifier.height(60.dp))


        }
    }
}

@Preview
@Composable
private fun ScreenPreview() {
    CartScreenUI(modifier = Modifier)
}