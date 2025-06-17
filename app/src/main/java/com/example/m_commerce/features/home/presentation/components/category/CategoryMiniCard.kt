package com.example.m_commerce.features.home.presentation.components.category

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import coil3.request.ImageRequest
import coil3.request.crossfade
import com.example.m_commerce.features.home.domain.entity.Category

@Composable
fun CategoryMiniCard(modifier: Modifier = Modifier, onClick: () -> Unit, category: Category) {
    Column(modifier = modifier.clip( RoundedCornerShape(topStart = 100.dp, topEnd = 100.dp, bottomStart = 12.dp, bottomEnd = 12.dp)).clickable {  onClick() }) {
        AsyncImage(
            modifier = Modifier
                .weight(1f).clip(shape = CircleShape),
            model = ImageRequest.Builder(LocalContext.current)
                .data(category.image)
                .crossfade(true)
                .build(),
            contentDescription = "Category image",
            contentScale = ContentScale.Crop
        )

        Spacer(modifier = Modifier.height(8.dp))
        Text(category.name,
            overflow = TextOverflow.Ellipsis,
            maxLines = 1,
            modifier = Modifier
            .fillMaxWidth()
            .background(Color.Yellow),
            style = TextStyle(fontSize = 16.sp, textAlign = TextAlign.Center),
        )
    }
}