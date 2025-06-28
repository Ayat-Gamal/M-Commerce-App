package com.example.m_commerce.core.shared.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight


@Composable
fun WidgetWithHeader(modifier: Modifier = Modifier, header: String, content: @Composable () -> Unit) {
    Column(modifier = modifier.fillMaxWidth().wrapContentHeight()) {
        Text(
            text = header,
            style = MaterialTheme.typography.headlineSmall.copy(
                fontWeight = FontWeight.Bold
            )
        )
        content()
    }
}