package com.example.m_commerce.features.cart.presentation.components

import ProductVariant
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.example.m_commerce.config.theme.Background
import com.example.m_commerce.config.theme.Black
import com.example.m_commerce.config.theme.Teal
import com.example.m_commerce.config.theme.White
import com.example.m_commerce.features.profile.presentation.viewmodel.CurrencyViewModel

@Composable
fun CartItemCard(
    product: ProductVariant, onIncrease: () -> Unit,
    onDecrease: () -> Unit, onRemove: () -> Unit, currencyViewModel: CurrencyViewModel
) {

    val availabilityCheck = remember { mutableStateOf(true) }


    LaunchedEffect(product.quantity) {
        if (product.quantity == 0) {
            availabilityCheck.value = false
        }
    }

    Box {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 16.dp, end = 16.dp, bottom = 8.dp),
            colors = CardDefaults.cardColors(
                containerColor = Background
            ),
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(12.dp),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                AsyncImage(
                    model = product.imageUrl,
                    contentDescription = "Product Image",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .size(64.dp)
                        .clip(RoundedCornerShape(12.dp))
                )

                Spacer(modifier = Modifier.width(12.dp))

                Column(
                    modifier = Modifier.weight(1f)
                ) {
                    Text(
                        text = product.productTitle ,
                        fontWeight = FontWeight.Bold)
                    Text(
                        text = product.title)
                    Spacer(modifier = Modifier.height(4.dp))
                    Log.i("TAG", "CartItemCard:${currencyViewModel.formatPrice(product.price)}")
                    Text(
                        text = "${currencyViewModel.formatPrice(product.price)}",
                    )
                }

                Spacer(modifier = Modifier.width(12.dp))

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(top = 40.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .size(20.dp)
                            .background(White, RoundedCornerShape(4.dp))
                            .border(1.dp, Color.LightGray, RoundedCornerShape(4.dp))
                            .clickable(onClick = onDecrease),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.Remove,
                            contentDescription = "Decrease",
                            tint = Black,
                            modifier = Modifier.size(12.dp)
                        )
                    }

                    Text(
                        text = (product.quantity).toString(),
                        modifier = Modifier.padding(horizontal = 8.dp)
                    )

                    Box(
                        modifier = Modifier
                            .size(20.dp)
                            .background(Teal, RoundedCornerShape(4.dp))
                            .clickable(onClick = onIncrease),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.Add,
                            contentDescription = "Increase",
                            tint = White,
                            modifier = Modifier.size(12.dp)
                        )
                    }
                }


            }
        if (!availabilityCheck.value) {
            Text("Currently Not Available", color = Color.Red )
        }
        }
        Icon(
            imageVector = Icons.Default.Close,
            contentDescription = "Delete",
            tint = Color.Red,
            modifier = Modifier
                .align(Alignment.TopEnd)
                .padding(4.dp)
                .padding(8.dp)
                .size(20.dp)
                .clickable {
                    Log.i("TAG", "CartItemCard:${product.toString()} ")
                    onRemove()
                }
        )
    }
}





