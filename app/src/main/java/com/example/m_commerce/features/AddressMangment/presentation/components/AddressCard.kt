package com.example.m_commerce.features.AddressMangment.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.outlined.LocationOn
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
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
            .clip(RoundedCornerShape(12.dp))
            .padding(start = 16.dp , end = 16.dp , top = 8.dp )
            .combinedClickable(
                onClick = { onSelect?.invoke() },
                onLongClick = { onLongPress?.invoke() }
            )
            .border(
                width = 1.dp,
                color = if (expanded) Teal else Color.LightGray,
                shape = RoundedCornerShape(12.dp)
            )

    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.Top
        ) {
            Icon(
                imageVector = Icons.Outlined.LocationOn,
                contentDescription = null,
                tint = Teal,
                modifier = Modifier.size(24.dp)
            )

            Spacer(modifier = Modifier.width(12.dp))

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = address.address2.ifEmpty { "Address" },
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = address.address1,
                    fontSize = 14.sp,
                    color = Color.Gray
                )
                Text(
                    text = "${address.city}, ${address.zip}",
                    fontSize = 12.sp,
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
            if(!isDefault && onDelete != null){
                Box(
                    contentAlignment = Alignment.TopEnd,
                    modifier = Modifier.background(Color.Transparent)
                ) {
                    IconButton(onClick = { expanded = true }) {
                        Icon(Icons.Default.MoreVert, contentDescription = "More options")
                    }

                    DropdownMenu(
                        expanded = expanded,
                        onDismissRequest = { expanded = false },
                        modifier = Modifier.background(Color.White)
                    ) {
                        DropdownMenuItem(
                            text = { Text("Delete") },
                            onClick = {
                                expanded = false
                                onDelete?.invoke()
                            }
                        )
                        DropdownMenuItem(
                            text = { Text("Set as Default") },
                            onClick = {
                                expanded = false
                                onLongPress?.invoke()
                            }
                        )
                    }
                }

            }
        }
    }
}
