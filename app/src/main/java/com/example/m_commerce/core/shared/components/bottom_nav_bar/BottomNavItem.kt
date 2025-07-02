// BottomNavItem.kt
package com.example.m_commerce.core.shared.components.bottom_nav_bar

import com.example.m_commerce.R
import com.example.m_commerce.config.routes.AppRoutes

sealed class BottomNavItem(
    val route: AppRoutes, val iconRes: Int, val label: String
) {
    object Home : BottomNavItem(AppRoutes.HomeScreen, R.raw.home, "Home")
    object Category : BottomNavItem(AppRoutes.CategoryScreen, R.raw.category, "Category")
    object Cart : BottomNavItem(AppRoutes.CartScreen, R.raw.cart, "Cart")
    object Profile : BottomNavItem(AppRoutes.ProfileScreen, R.raw.profile, "Profile")
}
