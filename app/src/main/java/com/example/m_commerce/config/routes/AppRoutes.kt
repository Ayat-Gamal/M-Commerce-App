package com.example.m_commerce.config.routes

import kotlinx.serialization.Serializable

@Serializable
sealed class AppRoutes {
    @Serializable
    object HomeScreen : AppRoutes()

    @Serializable
    object CategoryScreen : AppRoutes()

    @Serializable
    class CategoryDetailsScreen(val categoryId: String) : AppRoutes()

    @Serializable
    object BrandsScreen : AppRoutes()

    @Serializable
    data class BrandDetailsScreen(val brandId: String) : AppRoutes()

    @Serializable
    data class ProductDetailsScreen(val productId: String) : AppRoutes()

    @Serializable
    object CartScreen : AppRoutes()

    @Serializable
    object CheckoutScreen : AppRoutes()

    @Serializable
    object PaymentScreen : AppRoutes()

    @Serializable
    object ProfileScreen : AppRoutes()

    @Serializable
    object RegisterScreen : AppRoutes()

    @Serializable
    object LoginScreen : AppRoutes()

    @Serializable
    object ManageAddressScreen : AppRoutes()

    @Serializable
    object AddAddressScreen : AppRoutes()

    @Serializable
    object WishListScreen : AppRoutes()

    @Serializable
    object CreditCardDetails : AppRoutes()




}
