package com.example.m_commerce.features.profile.presentation.components.currency

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.ripple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import com.example.m_commerce.features.profile.domain.model.CurrencyDetails

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun CurrencyListItem(
    currency: CurrencyDetails,
    onCurrencyClick: (currency: CurrencyDetails) -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .combinedClickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = ripple(color = Color.Gray, bounded = true),
                onClick = { },
                onLongClick = {
                    onCurrencyClick(currency)
                    println("Long press detected")
                }
            )
            .padding(12.dp),
        colors = CardDefaults.cardColors(containerColor = Color.Transparent)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            AsyncImage(
                currency.icon,
                contentDescription = null,
                modifier = Modifier
                    .padding(start = 16.dp)
                    .size(24.dp)
            )
            Text(
                currency.countryCode,
                modifier = Modifier.padding(start = 12.dp),
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold
            )
            Text(
                currency.currencyName,
                modifier = Modifier.padding(start = 12.dp),
                fontSize = 16.sp,
            )
        }
    }
}
