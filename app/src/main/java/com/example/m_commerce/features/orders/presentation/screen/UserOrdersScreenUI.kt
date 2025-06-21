package com.example.m_commerce.features.orders.presentation.screen

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.m_commerce.core.shared.components.default_top_bar.DefaultTopBar
import com.example.m_commerce.features.orders.presentation.components.OrderTrackingCard

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun UserOrdersScreenUI(modifier: Modifier = Modifier, navController: NavHostController) {

    Scaffold(topBar = {
        DefaultTopBar(title = "My Orders", navController = navController)
    }) { padding ->
        LazyColumn(
            modifier = Modifier.padding(padding),
            contentPadding = PaddingValues(vertical = 18.dp)
        ) {
            items(9) {
                //TODO: Require Order Pojo
                OrderTrackingCard()
                if (it != 8) HorizontalDivider()
            }
        }
    }
}
