package com.example.m_commerce.features.product.presentation.components

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.m_commerce.core.shared.components.NetworkImage
import com.example.m_commerce.features.product.domain.entities.Product
import com.example.m_commerce.features.profile.presentation.viewmodel.CurrencyViewModel


@Composable
fun ProductCard(
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    deleteFromWishList: (() -> Unit)? = null,
    product: Product,/*ProductCardModel*/
    currencyViewModel: CurrencyViewModel = hiltViewModel()
) {

    val formattedPrice = String.format("%.2f", product.price.toDouble())
    val parts = formattedPrice.split(".")
    val intPrice = parts[0]
    val decPrice = parts[1]

    Log.d("TAG", "ProductCard: int=$intPrice, dec=$decPrice")


    Column(modifier = modifier
        .clip(shape = RoundedCornerShape(8.dp))
        .clickable { onClick() }) {
        Box(contentAlignment = Alignment.TopEnd) {
            NetworkImage(url = product.images[0], modifier = Modifier.height(200.dp))
            if (deleteFromWishList != null) IconButton(onClick = deleteFromWishList) {
                Box(
                    modifier = Modifier
                        .size(40.dp) // Size of the circular background
                        .background(color = Color.White.copy(alpha = 0.8f), shape = CircleShape)
                        .padding(8.dp) // Padding inside the circle
                ) {
                    Icon(
                        imageVector = Icons.Default.Favorite,
                        contentDescription = "Add to wish list",
                        tint = Color.Red,
                        modifier = Modifier.align(Alignment.Center)
                    )
                }
            }
        }
        // TODO product offer
//        if (product.offer != null) Text("${product.offer}% off",modifier = Modifier
//            .background(OfferColor)
//            .padding(vertical = 4.dp, horizontal = 8.dp), color = White)

        Column(modifier = Modifier.padding(bottom = 8.dp)) {
            Row(verticalAlignment = Alignment.Top) {
                Text(currencyViewModel.formatPrice(intPrice), style = TextStyle(fontSize = 24.sp))
                Text(decPrice)
            }
            Text(
                product.title,
                style = TextStyle(fontSize = 18.sp, fontWeight = FontWeight.Bold),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}