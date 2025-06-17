package com.example.m_commerce.features.categories.presentation.components

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.m_commerce.config.theme.Black
import com.example.m_commerce.core.shared.components.NetworkImage
import com.example.m_commerce.features.home.domain.entity.Category

@Composable
fun CategoryCard(modifier: Modifier = Modifier, onClick: () -> Unit, category: Category) {
    Column(modifier = modifier.border(1.dp, color = Black).clickable { onClick() }) {
        NetworkImage(url = category.image, modifier = Modifier.weight(1f))
        Text(
            category.name, modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            style = TextStyle(fontSize = 16.sp, textAlign = TextAlign.Center), overflow = TextOverflow.Ellipsis, maxLines = 1,
        )

    }
}