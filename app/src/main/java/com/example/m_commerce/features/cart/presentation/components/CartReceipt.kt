package com.example.m_commerce.features.cart.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.m_commerce.config.theme.Teal
import com.example.m_commerce.config.theme.White
import com.example.m_commerce.core.shared.components.CustomButton
import com.example.m_commerce.features.cart.data.model.ReceiptItem


@Composable
fun CartReceipt() {
    val receiptItems = listOf(
        ReceiptItem("Subtotal:", "$20.00"),
        ReceiptItem("Shipping:", "$5.00"),
        ReceiptItem("Discount:", "-$3.00"),
        ReceiptItem("Tax:", "$2.50"),
    )

    Column(modifier = Modifier.fillMaxWidth().padding(16.dp)) {

        Column(modifier = Modifier.fillMaxWidth()) {
            receiptItems.forEach { item ->
                CartReceiptItem(item)
            }
        }

        Divider(Modifier.padding( vertical = 8.dp , horizontal = 8.dp))
        CartReceiptItem(ReceiptItem("Total", "$27.50"))

        CustomButton(
            modifier = Modifier.padding(top = 16.dp),
            text = "Ceckout",
            backgroundColor = Teal,
            textColor = White,
            height = 50,
            cornerRadius = 12,
            onClick = { /* save action */ }
        )
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
        Text(text = item.title , fontWeight = FontWeight.Bold)
        Text(text = item.price)
    }
}

@Preview(showBackground = true)
@Composable
private fun CartReceiptPreviewItem() {
    CartReceiptItem(ReceiptItem("Subtotal", "$20.00"))
}




