package com.example.m_commerce.features.orders.presentation.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.m_commerce.config.theme.Gray
import com.example.m_commerce.core.shared.components.SmallButton

@Composable
fun ShippingAddressSection(
    modifier: Modifier = Modifier,
    shippingAddressTitle: String,
    shippingAddressDesc: String,
    onClickChange: () -> Unit
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 12.dp), verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(imageVector = Icons.Default.LocationOn, contentDescription = "Shipping Address")
        Spacer(modifier = Modifier.width(8.dp))
        Column(modifier = Modifier.weight(1f)) {
            Text(text = shippingAddressTitle, style = TextStyle(fontWeight = FontWeight.W700, fontSize = 18.sp))
            Text(
                text = shippingAddressDesc,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
                style = TextStyle(color = Gray, fontWeight = FontWeight.W400)
            )
        }

        SmallButton(label = "Change", onClick = onClickChange)

    }
}