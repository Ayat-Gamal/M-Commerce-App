package com.example.m_commerce.features.home.presentation.screens

import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.m_commerce.core.shared.components.Placeholder


@Composable
fun HomeScreenUI(modifier: Modifier = Modifier) {

    val scrollState = rememberScrollState()

    Column(Modifier.fillMaxSize().scrollable(state = scrollState, orientation = Orientation.Vertical)) {
        Placeholder(Modifier.fillMaxWidth().height(200.dp))
    }

}




