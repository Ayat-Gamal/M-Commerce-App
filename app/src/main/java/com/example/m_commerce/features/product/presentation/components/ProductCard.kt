package com.example.m_commerce.features.product.presentation.components

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
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
import com.example.m_commerce.core.shared.components.TagWithText
import com.example.m_commerce.core.utils.extentions.capitalizeFirstLetters
import com.example.m_commerce.features.product.domain.entities.Product
import com.example.m_commerce.features.profile.presentation.viewmodel.CurrencyViewModel


@SuppressLint("DefaultLocale")
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
//    val intPrice = parts[0]
    val currency = currencyViewModel.formatPrice(formattedPrice).split(" ").first()
    val price = currencyViewModel.formatPrice(formattedPrice).split(" ").last()
    val decPrice = price.split(".").last()
    val intPrice = price.split(".").first()

    val titleParts = product.title.split("|")
    val title: String = if (titleParts.size > 1) {
        titleParts[1].trim()
    } else {
        titleParts[0]
    }
    Log.d("TAG", "ProductCard: ${currency}  $formattedPrice")

    val shape = RoundedCornerShape(16.dp)
    val shapeOuter = RoundedCornerShape((16 * 1.3).dp)

    Column(modifier = modifier
        .shadow(elevation = 4.dp, spotColor = Gray, shape = shapeOuter, clip = true)
        .background(color = White, shape = shapeOuter)
        .clickable { onClick() }
        .clip(shape = shapeOuter)
        .padding(8.dp)
    ) {
        Box(modifier = Modifier.background(color = Color.Transparent), contentAlignment = Alignment.TopEnd) {
            NetworkImage(
                url = if (product.images.isNotEmpty()) product.images[0] else "",
                modifier = Modifier
                    .height(200.dp)
                    .clip(shape = shape)
            )


            TagWithText(product.brand.capitalizeFirstLetters(), modifier = Modifier
                .padding(6.dp)
                .align(Alignment.BottomStart))
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

