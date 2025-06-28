package com.example.m_commerce.features.orders.presentation.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.m_commerce.config.routes.AppRoutes
import com.example.m_commerce.config.theme.Gray
import com.example.m_commerce.core.shared.components.CustomButton
import com.example.m_commerce.core.shared.components.NetworkImage
import com.example.m_commerce.core.shared.components.SmallButton
import com.example.m_commerce.core.shared.components.WidgetWithHeader
import com.example.m_commerce.core.shared.components.default_top_bar.DefaultTopBar
import com.example.m_commerce.features.brand.presentation.screen.img
import com.example.m_commerce.features.orders.presentation.components.ShippingAddressSection


@Composable
fun CheckoutScreenUI(navController: NavHostController, modifier: Modifier = Modifier) {
    Scaffold(modifier = modifier, topBar = { DefaultTopBar(title = "Checkout", navController = navController) }) { padding ->

        Column(
            modifier = Modifier
                .padding(padding)
                .padding(horizontal = 12.dp)
                .verticalScroll(rememberScrollState())
        ) {
            WidgetWithHeader(header = "Shipping Address") {
                ShippingAddressSection(
                    shippingAddressTitle = "Home",
                    shippingAddressDesc = "1010 Seko ay 7aga 3nd el wala 7amda elly saken ta7t betna, and mesh ba7eb el franko bs a3mel eh bas",
                    onClickChange = {
                        navController.navigate(AppRoutes.ManageAddressScreen)
                    }
                )
                HorizontalDivider()
                Spacer(modifier = Modifier.height(12.dp))
            }
            WidgetWithHeader(header = "Order List") {
                for (i in 1..5) {
                    OrderCheckoutCard()
                    if (i != 5) HorizontalDivider(modifier = Modifier.padding(vertical = 6.dp))
                }
            }

            Spacer(modifier = Modifier.height(12.dp))
            CustomButton(
                onClick = { navController.navigate(AppRoutes.PaymentScreen) },
                modifier = Modifier.fillMaxWidth(),
                text = "Continue to Payment"
            )
            Spacer(modifier = Modifier.height(12.dp))
        }
    }
}

@Composable
fun OrderCheckoutCard(modifier: Modifier = Modifier) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(120.dp)
            .clip(RoundedCornerShape(8.dp))
    ) {
        NetworkImage(url = img, modifier = Modifier.width(100.dp))
        Column(
            modifier = Modifier
                .weight(1f)
                .height(120.dp)
                .padding(12.dp), verticalArrangement = Arrangement.SpaceBetween
        ) {
            Text(text = "Product Name")
            Text(text = "Product Desc")
            Text(text = "EGP 1000.00")
        }

    }
}



