package com.example.m_commerce.features.AddressMangment.presentation.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.m_commerce.R
import com.example.m_commerce.config.theme.Teal
import com.example.m_commerce.features.AddressMangment.domain.entity.Address

@Composable
fun AddressCard(
    address: Address,
    isDefault: Boolean,
    onLongPress: (() -> Unit)? = null,
    onSelect: (() -> Unit)? = null,
    onDelete: (() -> Unit)? = null,
    modifier: Modifier = Modifier
) {
    var expanded by remember { mutableStateOf(false) }
    Box(
        modifier = modifier
            .fillMaxWidth()
            .padding(12.dp)
            .border(
                width = 1.dp,
                color = if (isDefault) Teal else Color.LightGray,
                shape = RoundedCornerShape(24.dp)
            )
            .clip(RoundedCornerShape(24.dp))
//            .combinedClickable(
//                onClick = { onSelect?.invoke() },
//                onLongClick = { onLongPress?.invoke() }
//            )
    ) {
        Row(
            modifier = Modifier.padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
//            Icon(
//                imageVector = Icons.Outlined.LocationOn,
//                contentDescription = null,
//                tint = Teal,
//                modifier = Modifier.size(24.dp)
//            )
            Box {
                Image(
                    modifier = Modifier
                        .size(50.dp)
                        .border(2.dp, if (isDefault) Teal else Color.LightGray, CircleShape)
                        .clip(CircleShape),
                    painter = painterResource(R.drawable.map),
                    contentDescription = null,
                    contentScale = ContentScale.Crop
                )

            }

            Spacer(modifier = Modifier.width(12.dp))

            Column(modifier = Modifier.weight(1f)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = address.address2.ifEmpty { "Address" },
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold
                    )
                    OptionButton(isDefault, onDelete, onLongPress)
                }
                Text(
                    text = address.address1,
                    fontSize = 12.sp,
                    color = Color.Gray
                )
                Text(
                    text = "City: ${address.city}, zip: ${address.zip}",
                    fontSize = 11.sp,
                    color = Color.Gray
                )
                if (isDefault) {
                    Text(
                        text = "Default Address",
                        color = Teal,
                        fontSize = 12.sp,
                        modifier = Modifier.padding(top = 4.dp)
                    )
                }
            }
        }
    }
}

@Composable
private fun OptionButton(
    isDefault: Boolean,
    onDelete: (() -> Unit)?,
    onLongPress: (() -> Unit)?
) {
    var expanded by remember { mutableStateOf(false) }

    if (!isDefault && onDelete != null) {
        Box {
            Icon(
                Icons.Default.MoreVert,
                modifier = Modifier
                    .padding(4.dp)
                    .size(24.dp)
                    .clip(CircleShape)
                    .clickable { expanded = true },
                contentDescription = "More options"
            )

            DropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false },
                modifier = Modifier.background(Color.White)
            ) {
                DropdownMenuItem(
                    text = { Text("Set as Default") },
                    onClick = {
                        expanded = false
                        onLongPress?.invoke()
                    }
                )
                DropdownMenuItem(
                    text = { Text("Delete", color = Color.Red) },
                    onClick = {
                        expanded = false
                        onDelete.invoke()
                    }
                )
            }
        }
    }
}
