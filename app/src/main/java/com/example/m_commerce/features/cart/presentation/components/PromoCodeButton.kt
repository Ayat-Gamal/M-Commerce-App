package com.example.m_commerce.features.cart.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuDefaults.textFieldColors
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.m_commerce.config.theme.Teal
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
            .background(White, RoundedCornerShape(24.dp))
            .padding(horizontal = 8.dp, vertical = 0.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        TextField(
            value = promoCode,
            onValueChange = onPromoCodeChange,
            placeholder = { Text("Promo Code") },
            colors = textFieldColors(
                unfocusedIndicatorColor = Color.Transparent,
                focusedIndicatorColor = Color.Transparent,
                focusedContainerColor = Color.Transparent,
                unfocusedContainerColor = Color.Transparent,
                cursorColor = Teal
            ),
            modifier = Modifier
                .weight(1f)
                .padding(end = 8.dp)
                .height(56.dp),
            singleLine = true
        )

        Button(
            onClick = onApplyClick,
            shape = RoundedCornerShape(24.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Teal,
                contentColor = White
            ),
            contentPadding = PaddingValues(horizontal = 16.dp, vertical = 12.dp),
            modifier = Modifier.height(45.dp).padding(start = 8.dp)
        ) {
            Text("Apply")
        }

    }
}
