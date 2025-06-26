package com.example.m_commerce.features.orders.presentation.components

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.m_commerce.config.theme.Gray
import com.example.m_commerce.config.theme.White
import com.example.m_commerce.core.shared.components.NetworkImage
import com.example.m_commerce.core.utils.extentions.formatDate
import com.example.m_commerce.features.orders.data.model.variables.LineItem
import com.example.m_commerce.features.orders.domain.entity.OrderHistory
import com.example.m_commerce.features.profile.presentation.viewmodel.CurrencyViewModel


@Composable
fun OrderTrackingCard(modifier: Modifier = Modifier, order: OrderHistory ,currencyViewModel: CurrencyViewModel = hiltViewModel()) {

    var expanded by remember { mutableStateOf(false) }
    val rotationAngle by animateFloatAsState(
        targetValue = if (expanded) 180f else 0f,
        label = "ArrowRotation"
    )
    Column(
        Modifier
            .animateContentSize()
            .padding(vertical = 8.dp, horizontal = 12.dp)
            .shadow(elevation = 4.dp, shape = RoundedCornerShape(8.dp), clip = false)
            .background(White, shape = RoundedCornerShape(8.dp))
            .clip(RoundedCornerShape(8.dp))
            .clickable { expanded = !expanded }
            .padding(12.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            Text(
                text = "${order.id}  ${order.status}  ${order.createdAt.formatDate()}",
                style = TextStyle(fontSize = 16.sp, fontWeight = FontWeight.Bold)
            )
            Icon(
                imageVector = Icons.Default.KeyboardArrowDown,
                contentDescription = "Expand",
                modifier = Modifier.rotate(rotationAngle)
            )
        }

        if (expanded) {
            Text(modifier = Modifier.padding(vertical = 12.dp), text = order.shippedTo, style = TextStyle(color = Gray, fontSize = 12.sp))
            HorizontalDivider(Modifier.padding(bottom = 12.dp))
            Column {
                order.items.forEach { item -> LineItemCard(item = item) }
                Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                    Text(text = "Total")
                    Text(text = currencyViewModel.formatPrice(order.totalPrice))
                }
            }
        }
    }
}

@Composable
fun LineItemCard(modifier: Modifier = Modifier, item: LineItem) {

//    Log.d("LineItem", "LineItemCard: ${item.image} -- ${totalPrice}")

    val extractedTitle = item.title.split("|")
    val formattedTitle = extractedTitle[1].trim()

    val extractedSpecs = item.specs?.split("/")
    val size = extractedSpecs?.get(0)?.trim()
    val color = extractedSpecs?.get(1)?.trim()
    Column(/*Modifier.padding(bottom = 12.dp)*/) {
        Row(
            modifier = modifier
                .clip(RoundedCornerShape(8.dp)),
//                .padding(bottom = 12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            NetworkImage(
                url = item.image,
                modifier = Modifier
                    .height(100.dp)
                    .width(100.dp)
                    .clip(RoundedCornerShape(8.dp))
            )

            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(12.dp)
                    .wrapContentHeight(),
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                Text(text = formattedTitle, style = TextStyle(fontSize = 16.sp, fontWeight = FontWeight.Bold))
                if (size != null) Text(text = "Size: $size", style = TextStyle(fontSize = 16.sp, fontWeight = FontWeight.Bold))
                if (color != null) Text(text = "Color: $color", style = TextStyle(fontSize = 16.sp, fontWeight = FontWeight.Bold))
                Text(text = "Qty: ${item.quantity}")
            }
        }
        HorizontalDivider(Modifier.padding(vertical = 12.dp))
    }

}