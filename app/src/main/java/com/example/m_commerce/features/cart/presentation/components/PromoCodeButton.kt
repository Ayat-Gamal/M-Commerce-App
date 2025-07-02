package com.example.m_commerce.features.cart.presentation.components

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuDefaults.textFieldColors
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.m_commerce.config.theme.Teal
import com.example.m_commerce.config.theme.White
import com.example.m_commerce.core.shared.components.CustomButton
import com.example.m_commerce.features.cart.presentation.viewmodel.CartViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PromoCodeInput(
    promoCode: String,
    onPromoCodeChange: (String) -> Unit,
    onApplyClick: () -> Unit,
    modifier: Modifier = Modifier,
    cartViewModel: CartViewModel
) {
    val applyCouponLoading by cartViewModel.applyCouponLoading.collectAsStateWithLifecycle()

    LaunchedEffect(applyCouponLoading) {
        Log.i("TAG", "PromoCodeInput: $applyCouponLoading")
    }
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

        CustomButton(
            text = "Apply",
            onClick = onApplyClick,
            modifier = Modifier
                .height(45.dp)
                .padding(start = 8.dp),
            contentPadding = PaddingValues(horizontal = 16.dp, vertical = 12.dp),
            fontSize = 14,
            isLoading = applyCouponLoading
        )

//        Button(
//            onClick = onApplyClick,
//            shape = RoundedCornerShape(24.dp),
//            colors = ButtonDefaults.buttonColors(
//                containerColor = Teal,
//                contentColor = White
//            ),
//            contentPadding = PaddingValues(horizontal = 16.dp, vertical = 12.dp),
//            modifier = Modifier.height(45.dp).padding(start = 8.dp)
//        ) {
//            Text("Apply")
//        }

    }
}
