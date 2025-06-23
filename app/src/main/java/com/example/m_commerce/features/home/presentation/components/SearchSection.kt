package com.example.m_commerce.features.home.presentation.components

import android.util.Log
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.m_commerce.features.home.presentation.viewmodel.HomeViewModel


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchSection(modifier: Modifier = Modifier, viewModel: HomeViewModel = hiltViewModel()) {
    var searchQuery by remember { mutableStateOf("") }

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

        SearchBarWithClear(query = searchQuery, onQueryChange = {
            searchQuery = it
            viewModel.search(it)
        }, onClear = {
            searchQuery = ""
            viewModel.search("")
        })

    }
}