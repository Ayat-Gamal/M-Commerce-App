package com.example.m_commerce.features.home.presentation.screens

import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.rememberScrollState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.m_commerce.core.shared.components.Placeholder
import com.example.m_commerce.features.home.presentation.components.BrandsSection
import com.example.m_commerce.features.home.presentation.components.CategorySection
import com.example.m_commerce.features.home.presentation.components.SpecialOffersSection


@Composable
fun HomeScreenUI(modifier: Modifier = Modifier) {

    val scrollState = rememberScrollState()

    Column(Modifier
        .fillMaxSize()
        .scrollable(state = scrollState, orientation = Orientation.Vertical)) {

        Placeholder(
            modifier = Modifier
                .fillMaxWidth()
                .height(100.dp),
            title = "Search"
        )
        SpecialOffersSection()
        CategorySection()
        BrandsSection()
    }

}




