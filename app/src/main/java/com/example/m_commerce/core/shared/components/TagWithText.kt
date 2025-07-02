package com.example.m_commerce.core.shared.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.m_commerce.R

@Composable
fun TagWithText(tag: String, modifier: Modifier = Modifier) {
    Box(modifier = modifier) {
        Row(
            modifier = Modifier
                .background(color = Color.White.copy(alpha = 0.6f), shape = RoundedCornerShape(6.dp))
                .border(1.dp, Color.Gray, RoundedCornerShape(6.dp))
                .padding(4.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {

            SvgImage(resId = R.raw.tag, size = 12)
            Spacer(modifier = Modifier.width(2.dp))
            Text(
                text = tag,
                style = TextStyle(fontSize = 11.sp),
            )
        }
    }
}