package com.example.m_commerce.features.product.presentation.components

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.m_commerce.R
import com.example.m_commerce.config.theme.Gray
import com.example.m_commerce.config.theme.White
import com.example.m_commerce.core.shared.components.NetworkImage
import com.example.m_commerce.core.shared.components.SvgButton
import com.example.m_commerce.core.shared.components.SvgImage
import com.example.m_commerce.core.utils.extentions.capitalizeFirstLetters
import com.example.m_commerce.features.product.domain.entities.Product
import com.example.m_commerce.features.profile.presentation.viewmodel.CurrencyViewModel


@Composable
fun ProductCard(
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    deleteFromWishList: (() -> Unit)? = null,
    product: Product,
    currencyViewModel: CurrencyViewModel = hiltViewModel()
) {

    val formattedPrice = String.format("%.2f", product.price.toDouble())
    val parts = formattedPrice.split(".")
    val intPrice = parts[0]
    val decPrice = parts[1]
    val currency = currencyViewModel.formatPrice(formattedPrice).split(" ")[1]

    val titleParts = product.title.split("|")
    var title: String
    if (titleParts.size > 1) {
        title = titleParts[1].trim()
    } else {
        title = titleParts[0]
    }
    Log.d("TAG", "ProductCard: ${titleParts}")

    val shape = RoundedCornerShape(16.dp)

    Column(modifier = modifier
        .shadow(elevation = 4.dp, spotColor = Gray, shape = shape, clip = true)
        .background(color = White, shape = shape)
        .clickable { onClick() }
        .padding(8.dp)
        .clip(shape = shape)
    ) {
        Box(modifier = Modifier.background(color = Color.Transparent), contentAlignment = Alignment.TopEnd) {
            NetworkImage(
                url = if (product.images.isNotEmpty()) product.images[0] else "",
                modifier = Modifier
                    .height(200.dp)
                    .clip(shape = shape)
            )

            Row(
                modifier = Modifier
                    .align(Alignment.BottomStart)
                    .padding(6.dp)
                    .background(color = Color.White.copy(alpha = 0.6f), shape = RoundedCornerShape(6.dp))
                    .border(1.dp, Color.Gray, RoundedCornerShape(6.dp))
                    .padding(4.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                SvgImage(resId = R.raw.tag, size = 12)
                Spacer(modifier = Modifier.width(2.dp))
                Text(
                    text = product.brand.capitalizeFirstLetters(),
                    style = TextStyle(fontSize = 11.sp),
                )
            }
            if (deleteFromWishList != null)
                Box(
                    modifier = Modifier
                        .padding(6.dp)
                        .background(color = Color.White.copy(alpha = 0.4f), shape = CircleShape)
                ) {
            SvgButton(R.raw.heart_fill, size = 18, colorFilter = ColorFilter.tint(Color.Red)) {
                deleteFromWishList()
            }
        }
//                IconButton(onClick = deleteFromWishList) {
//                Box(
//                    modifier = Modifier
//                        .size(40.dp)
//                        .background(color = Color.White.copy(alpha = 0.8f), shape = CircleShape)
//                        .padding(8.dp)
//                ) {
//                    Icon(
//                        imageVector = Icons.Default.Favorite,
//                        contentDescription = "Add to wish list",
//                        tint = Color.Red,
//                        modifier = Modifier.align(Alignment.Center)
//                    )
//                }
//            }
        }
        Column(
            modifier = Modifier
                .padding(vertical = 8.dp)
                .background(color = Color.Transparent)
        ) {
            Row(verticalAlignment = Alignment.Top) {
                Text(intPrice, style = TextStyle(fontSize = 24.sp))
                Text(decPrice)
                Text(currency)
            }

            Text(
                title.capitalizeFirstLetters(),
                style = TextStyle(fontSize = 18.sp, fontWeight = FontWeight.Bold),
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}