package com.example.m_commerce.core.shared.components

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.m_commerce.config.theme.Black
import com.example.m_commerce.config.theme.Teal
import com.example.m_commerce.config.theme.TextBackground

@Composable
fun CustomDialog(
    showDialog: Boolean,
    title: String,
    message: String,
    confirmText: String = "Yes",
    dismissText: String = "Cancel",
    onConfirm: () -> Unit,
    onDismiss: () -> Unit
) {
    if (showDialog) {
        AlertDialog(
            onDismissRequest = onDismiss,
            title = {
                Text(text = title )
            },
            text = {
                Text(text = message ,  fontSize = 16.sp)
            },
            confirmButton = {
                TextButton(onClick = onConfirm) {
                    Text(confirmText , color = Teal)
                }
            },
            dismissButton = {
                TextButton(onClick = onDismiss) {
                    Text(dismissText , color = Black)
                }
            },
            containerColor = TextBackground,
            titleContentColor = Black,
            textContentColor = Black,
            shape = RoundedCornerShape(16.dp),

            )
    }
}
