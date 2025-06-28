package com.example.m_commerce.core.utils.extentions

import androidx.navigation.NavHostController
import com.example.m_commerce.config.routes.AppRoutes

fun NavHostController.navigateAndClear(route: AppRoutes) {
    navigate(route) {
        popUpTo(0) { inclusive = true }
    }
}
