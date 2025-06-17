package com.example.m_commerce.features.home.presentation.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.m_commerce.core.shared.components.Placeholder

@Composable
fun SpecialOffersSection(modifier: Modifier = Modifier, navigateToSpecialOffers: () -> Unit) {

    SectionTemplate(title = "Special Offers", seeAllOnClick = navigateToSpecialOffers) {
        Placeholder(modifier)
    }
}