package com.example.m_commerce.features.categories.presentation.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.m_commerce.R
import com.example.m_commerce.config.theme.Black
import com.example.m_commerce.config.theme.White
import com.example.m_commerce.core.utils.extentions.capitalizeFirstLetters
import com.example.m_commerce.core.utils.extentions.categoryImagesMapping
import com.example.m_commerce.features.categories.domain.entity.DisplayableCategory

@Composable
fun CategoryCard(modifier: Modifier = Modifier, onClick: () -> Unit, category: DisplayableCategory) {


    val mapping = categoryImagesMapping

    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
            .clip(RoundedCornerShape(24.dp))
            .clickable { onClick() }) {
        Image(
            painter = painterResource(mapping[category.name] ?: R.drawable.uncategorized),
            contentScale = ContentScale.Crop,
            contentDescription = null,
            modifier = Modifier.fillMaxSize()
        )
        if (category.name != "SALE" && mapping[category.name] != null) Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Black.copy(alpha = 0.3f), shape = RoundedCornerShape(24.dp))
        )
        if (category.name != "SALE" && mapping[category.name] != null) Text(
            category.name?.capitalizeFirstLetters() ?: "null",
            modifier = Modifier
                .drawBehind {
                    drawLine(
                        color = White,
                        start = Offset(0f, size.height),
                        end = Offset(size.width, size.height),
                        strokeWidth = 2f
                    )

                    drawLine(
                        color = White,
                        start = Offset(0f, 0f),
                        end = Offset(size.width, 0f),
                        strokeWidth = 2f
                    )
                }
                .padding(vertical = 8.dp, horizontal = 16.dp),
            style = TextStyle(fontSize = 24.sp, textAlign = TextAlign.Center),
            overflow = TextOverflow.Ellipsis,
            maxLines = 1,
            fontWeight = FontWeight.Bold,
            color = White
        )

    }
}
