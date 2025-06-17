package com.example.m_commerce.core.shared.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun CustomButton(
    modifier: Modifier = Modifier,
    text: String,
    height: Int = 56,
    cornerRadius: Int = 24,
    backgroundColor: Color = Color.LightGray,
    textColor: Color = Color.Unspecified,
    fontSize: Int = 24,
    onClick: () -> Unit
) {
    return Button(
        modifier = modifier
            .fillMaxWidth()
            .height(height.dp),
        shape = RoundedCornerShape(cornerRadius.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = backgroundColor,
            contentColor = textColor
        ),
        onClick = onClick
    ) {
        Text(text, fontSize = fontSize.sp, style = MaterialTheme.typography.bodyLarge)

    }
}