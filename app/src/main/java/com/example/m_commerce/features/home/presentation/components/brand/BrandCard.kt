package com.example.m_commerce.features.home.presentation.components.brand

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import coil3.request.ImageRequest
import coil3.request.crossfade
import com.example.m_commerce.config.theme.Black
import com.example.m_commerce.config.theme.White
import com.example.m_commerce.features.home.domain.entity.Brand

@Composable
fun BrandCard(
    modifier: Modifier = Modifier,
    brand: Brand,
    onClick: () -> Unit
) {
    Box(
        modifier = modifier
            .clip(shape = RoundedCornerShape(topStart = 32.dp, bottomEnd = 32.dp))
            .clickable { onClick() },
        contentAlignment = Alignment.BottomCenter
    ) {
        NetworkImage(
            modifier = Modifier
                .height(190.dp)
                .fillMaxWidth(),
            url = brand.image,
        )
        Text(
            modifier = Modifier
                .background(Black.copy(alpha = 0.5f))
                .padding(vertical = 8.dp, horizontal = 12.dp)
                .fillMaxWidth(),
            text = brand.name,
            style = TextStyle(fontSize = 16.sp, textAlign = TextAlign.Center, color = White),
            overflow = TextOverflow.Ellipsis,
            maxLines = 1
        )
    }
}




@Preview(showBackground = true)
@Composable
private fun PrevCard() {
    BrandCard(brand = brands[0]) {}
}
