package com.example.m_commerce.features.home.presentation.components.specialoffer

import SpecialOfferCard
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.m_commerce.features.coupon.domain.entity.Coupon
import com.example.m_commerce.features.home.presentation.components.SectionTemplate

@Composable
fun SpecialOffersSection(
    modifier: Modifier = Modifier,
    snackBarHostState: SnackbarHostState,
    couponCodes: List<Coupon>,
    navigateToSpecialOffers: (() -> Unit)? = null,
) {
    SectionTemplate(title = "Special Offers", seeAllOnClick = navigateToSpecialOffers ) {
        SpecialOfferCard(couponCodes = couponCodes,   snackBarHostState = snackBarHostState,)
    }
}