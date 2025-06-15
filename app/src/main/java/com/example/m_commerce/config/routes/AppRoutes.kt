// AppRoutes.kt
package com.example.m_commerce.config.routes

import kotlinx.serialization.Serializable

@Serializable
sealed class AppRoutes {
    @Serializable
    object HomeScreen : AppRoutes()

    @Serializable
    object CategoryScreen : AppRoutes()

    @Serializable
    object CartScreen : AppRoutes()

    @Serializable
    object ProfileScreen : AppRoutes()
}
