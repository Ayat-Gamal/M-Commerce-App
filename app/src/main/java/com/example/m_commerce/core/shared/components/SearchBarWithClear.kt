package com.example.m_commerce.core.shared.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp


@Composable
fun SearchBarWithClear(
    modifier: Modifier = Modifier,
    query: String = "",
    onQueryChange: (String) -> Unit,
    onClear: () -> Unit,
    enabled: Boolean = true,
    placeholder: String,
    onclick: (() -> Unit)? = null,
) {
    Row {
        TextField(
            value = query,
            onValueChange = onQueryChange,
            placeholder = { Text(placeholder) },
            leadingIcon = { Icon(Icons.Default.Search, contentDescription = "Search Icon") },
            trailingIcon = {
                if (query.isNotEmpty()) {
                    IconButton(onClick = onClear) {
                        Icon(Icons.Default.Clear, contentDescription = "Clear")
                    }
                }
            },
            singleLine = true,
            shape = RoundedCornerShape(12.dp),
            enabled = enabled,
            modifier = modifier
//                .weight(1f)
                .then(if (onclick != null) Modifier.clickable { onclick() } else Modifier)
                .fillMaxWidth()
                .height(54.dp)
                .clip(RoundedCornerShape(12.dp)),
        )
        Spacer(modifier = Modifier.width(8.dp))
        /*Surface(
            modifier = Modifier
                .size(54.dp)
                .clip(RoundedCornerShape(12.dp))
        ) {
            Box(contentAlignment = Alignment.Center, modifier = Modifier.fillMaxSize()) {
                Icon(
                    imageVector = Icons.Filled.AddCircle,
                    contentDescription = "Filter",
                    modifier = Modifier.size(29.dp),
                    tint = Color.Black // Optional
                )
            }
        }*/

    }
}

