package com.example.m_commerce.features.cart.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.m_commerce.config.theme.Background
import com.example.m_commerce.config.theme.Teal
import com.example.m_commerce.config.theme.TextBackground
import com.example.m_commerce.config.theme.White
import com.example.m_commerce.core.shared.components.CustomButton
import com.example.m_commerce.features.cart.data.model.ReceiptItem


@Composable
fun CartReceipt(paddingValues: PaddingValues) {
    val receiptItems = listOf(
        ReceiptItem("Subtotal:", "$20.00"),
        ReceiptItem("Shipping:", "$5.00"),
        ReceiptItem("Discount:", "-$3.00"),
        ReceiptItem("Tax:", "$2.50"),
    )

    Column(modifier = Modifier.background(Background)) {
//        HorizontalDivider(
//            thickness = 1.dp,
//            modifier = Modifier.padding(0.dp)
//        )

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .clip(shape = RoundedCornerShape(topEnd = 24.dp, topStart = 24.dp))
                .background(TextBackground)
                .padding(0.dp)
        ) {
            PromoCodeInput(
                promoCode = "",
                onPromoCodeChange = {},
                onApplyClick = {},
                modifier = Modifier.padding(16.dp)
            )
            Column(modifier = Modifier.fillMaxWidth()) {
                receiptItems.forEach { item ->
                    CartReceiptItem(item)
                }
            }

            Divider(Modifier.padding(vertical = 8.dp, horizontal = 8.dp))
            CartReceiptItem(ReceiptItem("Total", "$27.50"))

            var state by remember { mutableStateOf(false) }

            CustomButton(
                modifier = Modifier.padding(
                    start = 16.dp,
                    end = 16.dp,
                    bottom = paddingValues.calculateBottomPadding(),
                    top = 16.dp
                ),
                isLoading = state,
                text = "Ceckout",
                backgroundColor = Teal,
                textColor = White,
                height = 50,
                cornerRadius = 12,
                onClick = { state = true }
            )
        }
    }
}

@Composable
fun CartReceiptItem(item: ReceiptItem) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(text = item.title, fontWeight = FontWeight.Bold)
        Text(text = item.price)
    }
}

@Preview(showBackground = true)
@Composable
private fun CartReceiptPreviewItem() {
    CartReceiptItem(ReceiptItem("Subtotal", "$20.00"))
}




