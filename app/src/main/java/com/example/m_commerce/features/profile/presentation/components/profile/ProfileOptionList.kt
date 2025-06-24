package com.example.m_commerce.features.profile.presentation.components.profile

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Divider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.m_commerce.config.theme.dividerGray
import com.example.m_commerce.features.profile.domain.entity.ProfileOption

@Composable
fun ProfileOptionsList(
    items: List<ProfileOption>,
    modifier: Modifier = Modifier,
    onItemClick: (ProfileOption) -> Unit = {}
) {
    LazyColumn(modifier = modifier.fillMaxWidth()) {
        itemsIndexed(items) { index, option ->
            ProfileOptionItem(option = option, onClick = { onItemClick(option) })
            if (index < items.lastIndex) {
                Divider(color = dividerGray, thickness = 1.dp)
            }
        }
    }
}
