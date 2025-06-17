package com.example.m_commerce.features.profile.presentation.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.m_commerce.R
import com.example.m_commerce.config.theme.Teal
import com.example.m_commerce.features.profile.domain.model.ProfileOption

@Composable
fun ProfileOptionItem(option: ProfileOption, onClick: () -> Unit = {}) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .padding(vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            option.icon,
            contentDescription = option.title,
            modifier = Modifier.size(24.dp),
            tint = Teal
        )
        Spacer(modifier = Modifier.width(16.dp))
        Text(
            option.title,
            modifier = Modifier.weight(1f),
            fontSize = 16.sp,
        )
        Icon(
            Icons.Default.KeyboardArrowRight,
            contentDescription = "Go",
            modifier = Modifier.size(width = 24.dp, height = 24.dp), tint = Teal
        )
    }
}

