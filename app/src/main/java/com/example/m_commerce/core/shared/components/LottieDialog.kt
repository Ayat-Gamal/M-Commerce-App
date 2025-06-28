package com.example.m_commerce.core.shared.components


import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition

@Composable
fun LottieAlertDialog(
    showDialog: Boolean,
    onDismiss: () -> Unit,
    lottieRawResId: Int,
    lottieSize: Int = 150,
    text: String? = null,
    btnLabel: String
) {
    if (showDialog) {
        Dialog(onDismissRequest = onDismiss) {
            Surface(
                shape = MaterialTheme.shapes.medium,
                tonalElevation = 4.dp
            ) {
                Column(
                    modifier = Modifier
                        .padding(24.dp)
                        .wrapContentWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(lottieRawResId))
                    val progress by animateLottieCompositionAsState(
                        composition,
                        iterations = LottieConstants.IterateForever
                    )

                    LottieAnimation(
                        composition,
                        progress = { progress },
                        modifier = Modifier
                            .size(lottieSize.dp)
                            .padding(8.dp)
                    )

                    Spacer(modifier = Modifier.height(16.dp))
                    text?.let { Text(text) }
                    Spacer(modifier = Modifier.height(16.dp))

                    CustomButton(btnLabel) { onDismiss() }
                }
            }
        }
    }
}
