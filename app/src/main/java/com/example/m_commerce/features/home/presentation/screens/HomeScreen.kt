package com.example.m_commerce.features.home.presentation.screens

import androidx.activity.compose.BackHandler
import androidx.activity.compose.LocalActivity
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.m_commerce.features.home.domain.entity.Brand
import com.example.m_commerce.features.home.domain.entity.Category
import com.example.m_commerce.features.home.presentation.components.brand.BrandsSection
import com.example.m_commerce.features.home.presentation.components.category.CategorySection
import com.example.m_commerce.features.home.presentation.components.SearchSection
import com.example.m_commerce.features.home.presentation.components.specialoffer.SpecialOffersSection


@Composable
fun HomeScreenUI(
    modifier: Modifier = Modifier,
    navigateToCategories: () -> Unit,
    navigateToCategory: (Category) -> Unit,
    navigateToSpecialOffers: () -> Unit,
    navigateToBrands: () -> Unit,
    navigateToBrand: (Brand) -> Unit
) {


    val scrollState = rememberScrollState()
    val activity = LocalActivity.current
    BackHandler { activity?.finish() }

    Column(
        Modifier
            .verticalScroll(scrollState)
            .wrapContentHeight()
    ) {
        SearchSection()
        SpecialOffersSection(
            Modifier
                .fillMaxWidth()
                .height(200.dp), navigateToSpecialOffers
        )
        CategorySection(
            Modifier
                .fillMaxWidth()
                .height(120.dp), navigateToCategories, navigateToCategory)
        BrandsSection(Modifier.fillMaxWidth(), navigateToBrands, navigateToBrand)
        Spacer(Modifier.height(112.dp))
    }

}





