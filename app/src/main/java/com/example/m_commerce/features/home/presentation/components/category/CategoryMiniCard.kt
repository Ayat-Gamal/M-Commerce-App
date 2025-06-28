package com.example.m_commerce.features.home.presentation.components.category

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.m_commerce.R
import com.example.m_commerce.core.shared.components.SvgImage
import com.example.m_commerce.core.utils.extentions.capitalizeFirstLetters
import com.example.m_commerce.core.utils.extentions.categoryIconsMapping
import com.example.m_commerce.features.categories.domain.entity.Category

@Composable
fun CategoryMiniCard(modifier: Modifier = Modifier, onClick: () -> Unit, category: Category) {





    Row(
        modifier = modifier
            .border(1.dp, color = Color.Gray, shape = RoundedCornerShape(12.dp))
            .clip(RoundedCornerShape(12.dp))
            .clickable { onClick() }
            .padding(8.dp)
    ) {

        SvgImage(resId = categoryIconsMapping[category.name] ?: R.raw.other_category)

        Spacer(modifier = Modifier.width(8.dp))
        Text(
            category.name.capitalizeFirstLetters() ?: "NULL BRAND",
            overflow = TextOverflow.Ellipsis,
            maxLines = 1,
            modifier = Modifier
                .fillMaxWidth(),
            style = TextStyle(fontSize = 16.sp, textAlign = TextAlign.Start),
        )
    }
}