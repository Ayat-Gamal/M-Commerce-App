package com.example.m_commerce.features.cart.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.m_commerce.config.theme.Teal
import com.example.m_commerce.config.theme.TextBackground
import com.example.m_commerce.config.theme.White

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PromoCodeInput(
    promoCode: String,
    onPromoCodeChange: (String) -> Unit,
    onApplyClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .background(TextBackground, RoundedCornerShape(24.dp)) // Rounded background
            .padding(horizontal = 12.dp, vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        TextField(
            value = promoCode,
            onValueChange = onPromoCodeChange,
            placeholder = { Text("Promo Code") },
            colors = TextFieldDefaults.textFieldColors(
                containerColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                focusedIndicatorColor = Color.Transparent
            ),
            modifier = Modifier
                .weight(1f)
                .padding(end = 8.dp),
            singleLine = true
        )

        Button(
            onClick = onApplyClick,
            shape = RoundedCornerShape(24.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Teal,
                contentColor = White
            ),
            contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp)
        ) {
            Text("Apply")
        }
    }
}
