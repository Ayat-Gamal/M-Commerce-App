package com.example.m_commerce.features.profile.presentation.components.currency

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.m_commerce.config.theme.dividerGray
import com.example.m_commerce.features.profile.domain.entity.SymbolResponse


@Composable
fun CurrencyList(state: List<SymbolResponse>, modifier: Modifier, onCurrencyClick: (currency: SymbolResponse) -> Unit) {
    LazyColumn(modifier = modifier){
        items(state.size) { index ->
                CurrencyListItem(state[index] , onCurrencyClick)

            HorizontalDivider(
                color = dividerGray,
                thickness = 1.dp,
                modifier = Modifier.padding(horizontal = 16.dp))
        }
    }
}