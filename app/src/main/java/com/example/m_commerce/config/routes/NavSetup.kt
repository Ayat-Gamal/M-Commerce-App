package com.example.m_commerce.config.routes

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.m_commerce.features.home.presentation.screens.Greeting

@Composable
fun NavSetup(
    navController: NavHostController,
    snackBarHostState: SnackbarHostState,
    modifier: Modifier = Modifier
) {
    val startingScreen = AppRoutes.HomeScreen

    NavHost(
        navController = navController,
        startDestination = startingScreen,
        modifier = modifier.padding(16.dp)
    ) {
        composable<AppRoutes.HomeScreen> {
            Greeting(name = "HOME")
        }

        composable<AppRoutes.CategoryScreen> {
            Greeting(name = "CATEG")
        }
        composable<AppRoutes.CartScreen> {
            Greeting(name = "CART")
        }

        composable<AppRoutes.ProfileScreen> {
            Greeting(name = "PROFILE")
        }
    }
}
