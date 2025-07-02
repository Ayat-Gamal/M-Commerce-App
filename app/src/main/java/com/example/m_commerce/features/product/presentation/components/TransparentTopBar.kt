package com.example.m_commerce.features.product.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.m_commerce.R
import com.example.m_commerce.config.theme.White
import com.example.m_commerce.core.shared.components.SvgButton
import com.example.m_commerce.core.shared.components.default_top_bar.BackButton
import com.google.firebase.auth.FirebaseUser

@Composable
fun TransParentTopBar(
    navController: NavController,
    user: FirebaseUser?,
    isFavorite: Boolean,
    onFavoriteClick: () -> Unit
) {

    Row(
        Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        BackButton(
            navController = navController,
            modifier = Modifier
                .background(White.copy(0.5f), shape = CircleShape)
                .size(40.dp)
                .clip(CircleShape)
        )
        if (user != null) {
            SvgButton(
                modifier = Modifier
                    .background(White.copy(0.5f), shape = CircleShape)
                    .size(40.dp)
                    .clip(CircleShape),
                resId = if (isFavorite) R.raw.heart_fill else R.raw.heart_outline, colorFilter = ColorFilter.tint(Color.Red)) {
                onFavoriteClick()
            }
        }
    }
}