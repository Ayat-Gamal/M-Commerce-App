package com.example.m_commerce.features.orders.presentation.components

import android.util.Log
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import com.example.m_commerce.config.theme.Gray
import com.example.m_commerce.config.theme.White
import com.example.m_commerce.core.shared.components.NetworkImage
import com.example.m_commerce.core.shared.components.SmallButton
import com.example.m_commerce.features.orders.data.model.variables.LineItem
import com.example.m_commerce.features.orders.domain.entity.OrderHistory


@Composable
fun OrderTrackingCard(modifier: Modifier = Modifier, order: OrderHistory) {

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
            .clickable { expanded = !expanded  }
            .padding(12.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            Text(text = order.id, style = TextStyle(fontSize = 16.sp, fontWeight = FontWeight.Bold))
                Icon(
                    imageVector = Icons.Default.KeyboardArrowDown,
                    contentDescription = "Expand",
                    modifier = Modifier.rotate(rotationAngle)
                )
        }

        if (expanded) {

            Text(modifier = Modifier.padding(vertical = 12.dp), text = order.shippedTo, style = TextStyle(color = Gray, fontSize = 12.sp))
            HorizontalDivider(Modifier.padding(bottom = 12.dp))
            Column(modifier = Modifier.padding(bottom = 12.dp)) {
                order.items.forEach { item -> LineItemCard(item = item, totalPrice = order.totalPrice) }

            }

        }
    }

}

@Composable
fun LineItemCard(modifier: Modifier = Modifier, item: LineItem, totalPrice: String) {

    Log.d("LineItem", "LineItemCard: ${item.image} -- ${totalPrice}")

    Row(
        modifier = modifier
            .clip(RoundedCornerShape(8.dp))
    ) {
//        NetworkImage(
//            url = item.image,
//            modifier = Modifier
//                .height(100.dp)
//                .width(100.dp)
//                .clip(RoundedCornerShape(8.dp))
//        )

        Column(
            modifier = Modifier
                .weight(1f)
                .height(100.dp)
                .padding(12.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Text(text = item.title, style = TextStyle(fontSize = 16.sp, fontWeight = FontWeight.Bold))
            Text(text = item.specs ?: "", style = TextStyle(fontSize = 16.sp, fontWeight = FontWeight.Bold))
            Text(text = item.quantity.toString(), style = TextStyle(color = Gray, fontSize = 12.sp))
            Text(text = totalPrice)

        }
    }

}