package com.example.m_commerce.features.product.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp


@Composable
fun QuantitySelector(quantity: Int, onQuantityChange: (Int) -> Unit) {
    Row(
        Modifier
            .padding(4.dp)
            .background(
                Color.LightGray.copy(0.2f),
                RoundedCornerShape(25.dp)
            )
            .width(120.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Icon(
            Icons.Filled.Remove,
            contentDescription = "Decrease",
            Modifier
                .padding(8.dp)
                .background(White, shape = CircleShape)
                .clip(CircleShape)
                .clickable { if (quantity > 1) onQuantityChange(quantity - 1) }
                .padding(4.dp)
//                    .size(35.dp)
            ,
            tint = Color.DarkGray
        )

        Text(textAlign =  TextAlign.Center, text = quantity.toString(), fontSize = 16.sp, color = Color.Black)

        Icon(
            Icons.Filled.Add,
            contentDescription = "Decrease",
            Modifier
                .padding(8.dp)
                .background(White, shape = CircleShape)
                .clip(CircleShape)
                .clickable {
                    onQuantityChange(quantity + 1)
                }
                .padding(4.dp)
            ,
            tint = Color.DarkGray
        )

    }
}
