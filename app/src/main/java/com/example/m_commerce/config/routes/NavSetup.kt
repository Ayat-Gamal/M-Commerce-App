package com.example.m_commerce.config.routes

import AddAddressScreen
import ManageAddressScreen
import android.util.Log
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.example.m_commerce.core.utils.extentions.navigateAndClear
import com.example.m_commerce.features.auth.presentation.login.LoginScreen
import com.example.m_commerce.features.auth.presentation.register.RegisterScreen
import com.example.m_commerce.features.brand.presentation.screen.BrandScreenUI
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
    val startingScreen = AppRoutes.HomeScreen

    NavHost(
        navController = navController,
        startDestination = startingScreen,
        modifier = modifier.padding(0.dp)
    ) {

        composable<AppRoutes.HomeScreen> {
            showBottomNavbar.value = true
            HomeScreenUI(navigateToCategory = {
                navController.navigateAndClear(AppRoutes.CategoryScreen)
            }, navigateToSpecialOffers = {
                //TODO: @Tag - navigate to special offers here
            }, navigateToBrands = {

            }, navigateToBrand = { brand ->
                navController.navigate(AppRoutes.BrandScreen(brand.id))
            }
            )
        }

        composable<AppRoutes.BrandScreen> {
            showBottomNavbar.value = false
            val brandArgs = it.toRoute<AppRoutes.BrandScreen>()
            Log.i("TAG", "NavSetup:  ${brandArgs.brandId}")
            BrandScreenUI(brandId = brandArgs.brandId, navController = navController)
        }

        composable<AppRoutes.CategoryScreen> {
            CategoryScreenUI()
        }
        composable<AppRoutes.CartScreen> {
            CartScreenUI()
        }

        composable<AppRoutes.ProfileScreen> {
            showBottomNavbar.value = true
            ProfileScreenUI(navController)
        }

        composable<AppRoutes.RegisterScreen> {
            showBottomNavbar.value = false
            RegisterScreen(snackBarHostState) {
                navController.navigate(AppRoutes.LoginScreen)
            }
        }

        composable<AppRoutes.LoginScreen> {
            LoginScreen {
                navController.navigate(it)
            }
        }
        composable<AppRoutes.ManageAddressScreen> {
            showBottomNavbar.value = false
            ManageAddressScreen(navController)
        }
        composable<AppRoutes.AddAddressScreen> {
            AddAddressScreen(navController)
        }

    }
}
