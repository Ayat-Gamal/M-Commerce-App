package com.example.m_commerce.config.routes

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.m_commerce.core.utils.extentions.navigateAndClear
import com.example.m_commerce.features.auth.login.presentation.LoginScreen
import com.example.m_commerce.features.auth.register.presentation.RegisterScreen
import com.example.m_commerce.features.cart.presentation.screen.CartScreenUI
import com.example.m_commerce.features.categories.presentation.screen.CategoryScreenUI
import com.example.m_commerce.features.home.presentation.screens.HomeScreenUI
import com.example.m_commerce.features.profile.presentation.screen.ProfileScreenUI

@Composable
fun NavSetup(
    navController: NavHostController,
    snackBarHostState: SnackbarHostState,
    modifier: Modifier = Modifier,
    showBottomNavbar: MutableState<Boolean>
) {
    val startingScreen = AppRoutes.RegisterScreen

    NavHost(
        navController = navController,
        startDestination = startingScreen,
        modifier = modifier.padding(0.dp)
    ) {
        composable<AppRoutes.HomeScreen> {
            HomeScreenUI(navigateToCategory = {
                navController.navigateAndClear(AppRoutes.CategoryScreen)
            }, navigateToSpecialOffers = {
                //TODO: @Tag - navigate to special offers here
            }, navigateToBrands = {

            })
        }

        composable<AppRoutes.CategoryScreen> {
            CategoryScreenUI()
        }
        composable<AppRoutes.CartScreen> {
            CartScreenUI()
        }

        composable<AppRoutes.ProfileScreen> {
            ProfileScreenUI()
        }

        composable<AppRoutes.RegisterScreen> {
            showBottomNavbar.value = false
            RegisterScreen {
                navController.navigate(AppRoutes.LoginScreen)
            }
        }

        composable<AppRoutes.LoginScreen> {
            LoginScreen {
                navController.navigate(it)
            }
        }
    }
}
