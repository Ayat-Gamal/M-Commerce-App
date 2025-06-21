package com.example.m_commerce.features.orders.presentation.screen

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.m_commerce.config.theme.Gray
import com.example.m_commerce.config.theme.White
import com.example.m_commerce.core.shared.components.NetworkImage
import com.example.m_commerce.core.shared.components.SmallButton
import com.example.m_commerce.core.shared.components.default_top_bar.DefaultTopBar
import com.example.m_commerce.features.brand.presentation.screen.img
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
                OrderTrackingCard(
                    modifier = modifier
                        .padding(12.dp)
                        .shadow(elevation = 4.dp, shape = RoundedCornerShape(8.dp), clip = false)
                        .background(White, shape = RoundedCornerShape(8.dp)),
                    onTrackOrderClick = {}
                )
                if (it != 8) HorizontalDivider()
            }
        }
    }
}
