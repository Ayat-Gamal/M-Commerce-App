package com.example.m_commerce.core.shared.components

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.m_commerce.config.theme.Teal
import com.example.m_commerce.config.theme.White

@Composable
fun CustomButton(
    text: String,
    modifier: Modifier = Modifier,
    height: Int = 56,
    cornerRadius: Int = 24,
    backgroundColor: Color = Teal,
    textColor: Color = Color.Unspecified,
    isLoading: Boolean = false,
    fontSize: Int = 24,
    contentPadding: PaddingValues = PaddingValues(),
    isCart: Boolean = false,
    onClick: () -> Unit
) {
    return Button(
        modifier = modifier
//            .fillMaxWidth()
            .height(height.dp),
        //shape = RoundedCornerShape(cornerRadius.dp),
        enabled = !isLoading,
        colors = ButtonDefaults.buttonColors(
            containerColor = backgroundColor,
            contentColor = textColor,
            disabledContainerColor = backgroundColor.copy(alpha = 0.7f),
        ),
        contentPadding = contentPadding,
        onClick = onClick
    ) {
        if (isLoading) {
            CircularProgressIndicator(
                color = White,
                strokeWidth = 2.dp,
                modifier = Modifier.size(20.dp)
            )
        } else {
            Row {
                if (isCart) {
                    Icon(
                        modifier = Modifier.padding(start = 16.dp, end = 12.dp),
                        imageVector = Icons.Default.ShoppingCart,
                        contentDescription = null
                    )
                }

                Text(
                    text,
                    fontSize = fontSize.sp,
                    style = MaterialTheme.typography.bodyLarge,
                    modifier = if (isCart) Modifier.padding(end = 16.dp) else Modifier
                )
            }
        }
    }
}