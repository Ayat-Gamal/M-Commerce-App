package com.example.m_commerce.features.home.presentation.components.specialoffer

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.m_commerce.features.home.presentation.components.SectionTemplate

@Composable
fun SpecialOffersSection(modifier: Modifier = Modifier, navigateToSpecialOffers: () -> Unit) {
    SectionTemplate(title = "Special Offers", seeAllOnClick = navigateToSpecialOffers) {
        SpecialOfferCard()
    }
}