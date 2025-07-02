package com.example.m_commerce.features.product.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.m_commerce.config.theme.Teal
import com.example.m_commerce.core.shared.components.CustomButton


@Composable
fun BottomBar(price: String, isLoading: Boolean, onAddToCart: () -> Unit) {
    Row(
        Modifier
            .fillMaxWidth()
            .padding(horizontal = 24.dp, vertical = 12.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row {
            Text("$", color = Teal, fontWeight = FontWeight.Bold, fontSize = 30.sp)
            Text(
                text = price,
                color = Color.DarkGray,
                fontWeight = FontWeight.Bold,
                fontSize = 30.sp,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }
        Spacer(Modifier.width(16.dp))
        CustomButton(
            onClick = onAddToCart,
            text = "Add to Cart",
            modifier = Modifier.fillMaxWidth(),
            isLoading = isLoading,
            fontSize = 18,
            contentPadding = PaddingValues(horizontal = 16.dp),
            isCart = true
        )
    }
}