package com.example.m_commerce.features.cart.presentation.components

import ProductVariant
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
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
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.m_commerce.config.theme.Background
import com.example.m_commerce.config.theme.White
import com.example.m_commerce.core.shared.components.NetworkImage
import com.example.m_commerce.core.utils.extentions.capitalizeFirstLetter
import com.example.m_commerce.core.utils.extentions.capitalizeFirstLetters
import com.example.m_commerce.features.profile.presentation.viewmodel.CurrencyViewModel

@Composable
fun CartItemCard(
    product: ProductVariant, onIncrease: () -> Unit,
    onDecrease: () -> Unit, onRemove: () -> Unit, currencyViewModel: CurrencyViewModel
) {

    val availabilityCheck = remember { mutableStateOf(true) }

    val specs = product.title.split("/")
    val color = specs[1].trim()
    val size = specs[0].trim()

    val priceArr = currencyViewModel.formatPrice(product.price).split(" ")
    val currency = priceArr[0].trim()
    val price = priceArr[1].trim()

    LaunchedEffect(product.quantity) {
        if (product.quantity == 0) {
            availabilityCheck.value = false
        }
    }

    Box {
        Card(
            modifier = Modifier
                .fillMaxWidth(),
            colors = CardDefaults.cardColors(
                containerColor = Background
            ),
        ) {
            Row(
                modifier = Modifier
                    .padding(16.dp)
                    .shadow(2.dp, shape = RoundedCornerShape(12.dp), clip = true)
                    .background(White, shape = RoundedCornerShape(12.dp))
                    .fillMaxWidth()
                    .padding(12.dp),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                NetworkImage(
                    modifier = Modifier
                        .size(80.dp)
                        .clip(RoundedCornerShape(12.dp)),
                    url = product.imageUrl,
                )

                Spacer(modifier = Modifier.width(12.dp))

                Column(
                    modifier = Modifier.weight(1f)
                ) {
                    Text(
                        text = product.productTitle.capitalizeFirstLetters(),
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = "Clr: $color",
                    )
//                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "Size: $size",
                    )
                    Spacer(modifier = Modifier.height(4.dp))

                    Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                        ProductQuantity(onDecrease, product, onIncrease)
                        ItemPrice(currency, price)
                    }
                }
            }
            if (!availabilityCheck.value) {
                Text("Currently Not Available", color = Color.Red)
            }
        }
        Icon(
            imageVector = Icons.Default.Close,
            contentDescription = "Delete",
            tint = Color.Red,
            modifier = Modifier
                .padding(26.dp)
                .align(Alignment.TopEnd)
                .size(24.dp)
                .background(Color.Red.copy(0.1f), shape = CircleShape)
                .clip(CircleShape)
                .clickable {
                    Log.i("TAG", "CartItemCard:${product.toString()} ")
                    onRemove()
                }
                .padding(3.dp)
        )
    }
}

@Composable
private fun ItemPrice(currency: String, price: String) {
    Row(verticalAlignment = Alignment.Top) {
        Text(
            text = currency,
            fontSize = 12.sp,
            fontWeight = FontWeight.Bold,
        )
        Spacer(modifier = Modifier.width(4.dp))

        Text(
            text = price,
            fontWeight = FontWeight.Bold,
            fontSize = 24.sp
        )
    }
}

@Composable
fun ProductQuantity(onDecrease: () -> Unit, product: ProductVariant, onIncrease: () -> Unit) {
    Row(
        Modifier
            .padding(2.dp)
            .background(
                Color.Gray.copy(0.2f),
                RoundedCornerShape(25.dp)
            )
            .width(100.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        QuantityIconButton(icon = Icons.Default.Remove) {
            onDecrease()
        }
        Text(textAlign = TextAlign.Center, text = (product.quantity).toString(), fontSize = 16.sp, color = Color.Black)
        QuantityIconButton(icon = Icons.Default.Add) {
            onIncrease()
        }
    }
}

@Composable
fun QuantityIconButton(modifier: Modifier = Modifier, icon: ImageVector, onClick: () -> Unit) {


    Icon(
        icon,
        contentDescription = null,
        modifier
            .padding(4.dp)
            .background(Color.White, shape = CircleShape)
            .clip(CircleShape)
            .clickable(
                onClick = onClick,
            )
            .padding(2.dp),
        tint = Color.DarkGray
    )
}





