package com.example.m_commerce.features.orders.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.m_commerce.config.theme.Gray
import com.example.m_commerce.core.shared.components.NetworkImage
import com.example.m_commerce.core.shared.components.SmallButton
import com.example.m_commerce.features.brand.presentation.screen.img


@Composable
fun OrderTrackingCard(modifier: Modifier = Modifier, onTrackOrderClick: () -> Unit) {

    Row(
        modifier = modifier
            .clip(RoundedCornerShape(8.dp))
    ) {
        NetworkImage(
            url = img,
            modifier = Modifier
                .height(100.dp)
                .width(100.dp)
        )

        Column(
            modifier = Modifier
                .weight(1f)
                .height(100.dp)
                .padding(12.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Text(text = "Product Name", style = TextStyle(fontSize = 16.sp, fontWeight = FontWeight.Bold))
            Text(text = "Product Desc", style = TextStyle(color = Gray, fontSize = 12.sp))
            Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                Text(text = "EGP 1000.00")
                SmallButton(label = "Track Order", onClick = onTrackOrderClick)
            }
        }
    }

}