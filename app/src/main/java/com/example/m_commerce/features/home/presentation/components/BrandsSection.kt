package com.example.m_commerce.features.home.presentation.components

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.m_commerce.core.shared.components.Placeholder

@Composable
fun BrandsSection(modifier: Modifier = Modifier, navigateToBrands: () -> Unit) {

    SectionTemplate(title = "Brands", seeAllOnClick = navigateToBrands) {
        Placeholder(modifier)
    }
}