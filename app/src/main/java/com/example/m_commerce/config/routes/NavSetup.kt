package com.example.m_commerce.config.routes

import CartScreenUI
import androidx.compose.foundation.layout.PaddingValues
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
import com.example.m_commerce.features.AddressMangment.presentation.screen.AddAddressScreen
import com.example.m_commerce.features.AddressMangment.presentation.screen.ManageAddressScreenUi
import com.example.m_commerce.features.AddressMangment.presentation.screen.MapScreenUi
import com.example.m_commerce.features.auth.presentation.login.LoginScreen
import com.example.m_commerce.features.auth.presentation.register.RegisterScreen
import com.example.m_commerce.features.brand.presentation.screen.BrandScreenUI
import com.example.m_commerce.features.brand.presentation.screen.BrandsScreenUI
import com.example.m_commerce.features.categories.presentation.screen.CategoryDetailsScreenUI
import com.example.m_commerce.features.categories.presentation.screen.CategoryScreenUI
import com.example.m_commerce.features.home.presentation.screens.HomeScreenUI
import com.example.m_commerce.features.orders.presentation.screen.CheckoutScreenUI
import com.example.m_commerce.features.orders.presentation.screen.UserOrdersScreenUI
import com.example.m_commerce.features.payment.presentation.screen.PaymentScreenUI
import com.example.m_commerce.features.payment.prsentation.screen.CreditCardDetailsUiLayout
import com.example.m_commerce.features.product.presentation.screen.ProductDetailsScreenUI
import com.example.m_commerce.features.profile.presentation.screen.CurrencyUiLayout
import com.example.m_commerce.features.profile.presentation.screen.HelpCenterScreenUiLayout
import com.example.m_commerce.features.profile.presentation.screen.ProfileScreenUI
import com.example.m_commerce.features.wishlist.presentation.WishListScreen
import com.google.firebase.auth.FirebaseAuth

@Composable
fun NavSetup(
    navController: NavHostController,
    snackBarHostState: SnackbarHostState,
    modifier: Modifier = Modifier,
    showBottomNavbar: MutableState<Boolean>,
    paddingValues: PaddingValues

) {
    val user = FirebaseAuth.getInstance().currentUser
    val startingScreen = if (user != null) AppRoutes.HomeScreen else AppRoutes.LoginScreen


    NavHost(
        navController = navController,
        startDestination = startingScreen,
        modifier = modifier.padding(0.dp)
    ) {

        composable<AppRoutes.HomeScreen> {
            showBottomNavbar.value = true
            HomeScreenUI(navigateToCategories = {
                navController.navigateAndClear(AppRoutes.CategoryScreen)
            }, navigateToSpecialOffers = {
                //TODO: @Tag - navigate to special offers here
            }, navigateToBrands = {
                navController.navigate(AppRoutes.BrandsScreen)
            }, navigateToBrand = { brand ->
                navController.navigate(AppRoutes.BrandDetailsScreen(brand.id !!))
            },
                navigateToCategory = { category ->
                    navController.navigate(AppRoutes.CategoryDetailsScreen(category.id))
                }
            )
        }

        composable<AppRoutes.BrandsScreen> {
            showBottomNavbar.value = false
            BrandsScreenUI(navController = navController)
        }
        composable<AppRoutes.BrandDetailsScreen> {
            showBottomNavbar.value = false
            val brandArgs = it.toRoute<AppRoutes.BrandDetailsScreen>()
            BrandScreenUI(brandId = brandArgs.brandId, navController = navController)
        }

        composable<AppRoutes.ProductDetailsScreen> {
            showBottomNavbar.value = false
            val productArgs = it.toRoute<AppRoutes.ProductDetailsScreen>()
            ProductDetailsScreenUI(productId = productArgs.productId, navController = navController)
        }

        composable<AppRoutes.CategoryScreen> {
            showBottomNavbar.value = true
            CategoryScreenUI { category ->
                navController.navigate(AppRoutes.CategoryDetailsScreen(category.id))
            }
        }

        composable<AppRoutes.CategoryDetailsScreen> {
            showBottomNavbar.value = false
            val categoryArgs = it.toRoute<AppRoutes.CategoryDetailsScreen>()
            CategoryDetailsScreenUI(
                categoryId = categoryArgs.categoryId,
                navController = navController
            )
        }

        composable<AppRoutes.CartScreen> {
            CartScreenUI(paddingValues)
        }

        composable<AppRoutes.ProfileScreen> {
            showBottomNavbar.value = true
            ProfileScreenUI(navController)
        }

        composable<AppRoutes.RegisterScreen> {
            showBottomNavbar.value = false
            RegisterScreen(snackBarHostState) {
                navController.navigateUp()
            }
        }

        composable<AppRoutes.CheckoutScreen> {
            showBottomNavbar.value = false
            CheckoutScreenUI(navController)
        }

        composable<AppRoutes.PaymentScreen> {
            showBottomNavbar.value = false
            PaymentScreenUI(navController)
        }

        composable<AppRoutes.UserOrdersScreen> {
            showBottomNavbar.value = false
            UserOrdersScreenUI(navController = navController)
        }


        composable<AppRoutes.LoginScreen> {
            LoginScreen(snackBarHostState = snackBarHostState) {
                navController.navigate(it)
            }
        }
        composable<AppRoutes.ManageAddressScreen> {
            showBottomNavbar.value = false
            ManageAddressScreenUi(navController)
        }
        composable<AppRoutes.AddAddressScreen> {
            val args = it.toRoute<AppRoutes.AddAddressScreen>()
            AddAddressScreen(navController, lat = args.lat , lng = args.lng)
        }
        composable<AppRoutes.CreditCardDetails> {
            showBottomNavbar.value = false
            CreditCardDetailsUiLayout(navController)
        }

        composable<AppRoutes.WishListScreen> {
            showBottomNavbar.value = false
            WishListScreen(navController, paddingValues)
        }
        composable<AppRoutes.CurrencyScreen> {
            CurrencyUiLayout(navController)
        }
        composable<AppRoutes.MapScreen> {
            MapScreenUi(navController,)
        }
        composable<AppRoutes.HelpCenterScreen> {
            showBottomNavbar.value = false
            HelpCenterScreenUiLayout(navController,)
        }

    }
}
