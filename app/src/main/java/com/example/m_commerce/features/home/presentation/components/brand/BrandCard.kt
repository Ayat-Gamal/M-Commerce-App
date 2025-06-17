package com.example.m_commerce.features.home.presentation.components.brand

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import coil3.request.ImageRequest
import coil3.request.crossfade
import com.example.m_commerce.features.home.domain.entity.Brand

@Composable
fun BrandCard(
    modifier: Modifier = Modifier,
    brand: Brand
) {
    Column(
        modifier = modifier
    ) {
        AsyncImage(
            modifier = Modifier
                .height(100.dp)
                .fillMaxWidth(),
            model = ImageRequest.Builder(LocalContext.current)
                .data(brand.image)
                .crossfade(true)
                .build(),
            contentDescription = "Brand image",
            contentScale = ContentScale.Crop
        )
        Text(modifier = Modifier.fillMaxWidth(), text = brand.name, style = TextStyle(fontSize = 16.sp, textAlign = TextAlign.Center))
    }
}


@Preview(showBackground = true)
@Composable
private fun PrevCard() {
    BrandCard(brand = brands[0])
}
