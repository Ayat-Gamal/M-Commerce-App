package com.example.m_commerce.features.profile.domain.usecase

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CardGiftcard
import androidx.compose.material.icons.filled.CreditCard
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Help
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.ShoppingCart
import com.example.m_commerce.features.profile.domain.entity.ProfileOption
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class GetProfileOptionsUseCase {
    operator fun invoke(): Flow<List<ProfileOption>> = flow {
        emit(
            listOf(
                ProfileOption("Your profile", Icons.Default.Person),
                ProfileOption("Manage Address", Icons.Default.LocationOn),
                ProfileOption("Payment Methods", Icons.Default.CreditCard),
                ProfileOption("My Orders", Icons.Default.ShoppingCart),
                ProfileOption("My Wishlist", Icons.Default.Favorite),
                ProfileOption("My Coupons", Icons.Default.CardGiftcard),
                ProfileOption("Settings", Icons.Default.Settings),
                ProfileOption("Help Center", Icons.Default.Help)
            )
        )
    }
}
