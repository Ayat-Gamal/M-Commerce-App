package com.example.m_commerce.features.brand.presentation.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.m_commerce.R
import com.example.m_commerce.config.theme.Teal
import com.example.m_commerce.config.theme.White
import com.example.m_commerce.core.shared.components.NetworkImage
import com.example.m_commerce.core.utils.extentions.brandImagesMapping
import com.example.m_commerce.core.utils.extentions.capitalizeFirstLetter
import com.example.m_commerce.core.utils.extentions.categoryImagesMapping
import com.example.m_commerce.features.brand.domain.entity.Brand

@Composable
fun BrandCard(
    modifier: Modifier = Modifier,
    brand: Brand,
    onClick: () -> Unit
) {

    val mapping = brandImagesMapping + categoryImagesMapping

    val shape = RoundedCornerShape(24.dp)

    Column(
        modifier = modifier
            .shadow(elevation = 4.dp, shape = shape, clip = false)
            .clip(shape = shape)
            .clickable { onClick() },
//            contentAlignment = Alignment.BottomCenter
    ) {
        if (mapping[brand.name] != null) {

            Image(
                modifier = Modifier
                    .height(190.dp)
                    .fillMaxWidth(),
                contentDescription = "",
                contentScale = ContentScale.Crop,
                painter = painterResource(id = mapping[brand.name] ?: R.drawable.uncategorized),
            )
        } else {
            brand.image?.let {
                NetworkImage(
                    modifier = Modifier
                        .height(190.dp)
                        .fillMaxWidth(),
                    url = it,
                )
            }
        }
        brand.name?.let {
            Text(
                modifier = Modifier
                    .background(Teal)
                    .padding(vertical = 8.dp, horizontal = 12.dp)
                    .fillMaxWidth(),
                text = it.capitalizeFirstLetter(),
                style = TextStyle(fontSize = 18.sp, textAlign = TextAlign.Center, color = White),
                overflow = TextOverflow.Ellipsis,
                maxLines = 1,
                fontWeight = FontWeight.Bold
            )
        }
    }
}
