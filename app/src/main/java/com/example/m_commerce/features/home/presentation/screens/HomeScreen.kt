package com.example.m_commerce.features.home.presentation.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.m_commerce.features.home.presentation.components.BrandsSection
import com.example.m_commerce.features.home.presentation.components.CategorySection
import com.example.m_commerce.features.home.presentation.components.SearchSection
import com.example.m_commerce.features.home.presentation.components.SpecialOffersSection


@Composable
fun HomeScreenUI(
    modifier: Modifier = Modifier,
    navigateToCategory: () -> Unit,
    navigateToSpecialOffers: () -> Unit,
    navigateToBrands: () -> Unit
) {

    val scrollState = rememberScrollState()

    Column(
        Modifier
            .fillMaxSize()
            .verticalScroll(scrollState)
    ) {

        SearchSection(
            Modifier
                .fillMaxWidth()
                .height(170.dp)
        )
        SpecialOffersSection(
            Modifier
                .fillMaxWidth()
                .height(200.dp), navigateToSpecialOffers
        )
        CategorySection(
            Modifier
                .fillMaxWidth()
                .height(90.dp), navigateToCategory
        )
        BrandsSection(
            Modifier
                .fillMaxWidth()
                .height(600.dp), navigateToBrands
        )

        Spacer(Modifier.height(112.dp))
    }

}





