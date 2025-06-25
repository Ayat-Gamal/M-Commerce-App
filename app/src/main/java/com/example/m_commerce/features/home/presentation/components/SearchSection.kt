package com.example.m_commerce.features.home.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.m_commerce.config.routes.AppRoutes
import com.example.m_commerce.core.shared.components.SearchBarWithClear
import com.example.m_commerce.features.home.presentation.viewmodel.HomeViewModel


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchSection(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    viewModel: HomeViewModel = hiltViewModel()
) {

    Column(
        modifier
            .clip(shape = RoundedCornerShape(bottomEnd = 18.dp, bottomStart = 18.dp))
            .background(Color.Gray)
            .height(170.dp)
            .padding(16.dp),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Text("Location")
        Row {
            Icon(
                imageVector = Icons.Filled.LocationOn, contentDescription = "Home"
            )
            Text("New York, USA")
        }

        SearchBarWithClear(
            onQueryChange = {},
            onClear = {},
            enabled = false,
            placeholder = "Search...",
            onclick = {
                navController.navigate(AppRoutes.SearchScreen(false))
            },
        )

    }
}