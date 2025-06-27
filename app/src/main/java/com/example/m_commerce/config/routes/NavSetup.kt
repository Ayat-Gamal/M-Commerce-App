package com.example.m_commerce.config.routes

import CartScreenUI
import android.os.Build
import androidx.annotation.RequiresApi
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
import com.example.m_commerce.features.brand.presentation.screen.BrandsDetailsScreenUI
import com.example.m_commerce.features.categories.presentation.screen.CategoryDetailsScreenUI
import com.example.m_commerce.features.categories.presentation.screen.CategoryScreenUI
import com.example.m_commerce.features.home.presentation.screens.HomeScreenUI
import com.example.m_commerce.features.onboarding.presentation.screen.OnboardingScreenUI
import com.example.m_commerce.features.orders.presentation.screen.CheckoutScreenUI
import com.example.m_commerce.features.orders.presentation.screen.UserOrdersScreenUI
import com.example.m_commerce.features.payment.presentation.screen.CreditCardDetailsUiLayout
import com.example.m_commerce.features.payment.presentation.screen.PaymentScreenUI
import com.example.m_commerce.features.product.presentation.screen.ProductDetailsScreenUI
import com.example.m_commerce.features.profile.presentation.screen.CurrencyScreenUi
import com.example.m_commerce.features.profile.presentation.screen.HelpCenterScreenUiLayout
import com.example.m_commerce.features.profile.presentation.screen.ProfileScreenUI
import com.example.m_commerce.features.search.presentation.SearchScreen
import com.example.m_commerce.features.wishlist.presentation.WishListScreen
import com.google.firebase.auth.FirebaseAuth
import com.stripe.android.paymentsheet.PaymentSheet

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun NavSetup(
    navController: NavHostController,
    snackBarHostState: SnackbarHostState,
    modifier: Modifier = Modifier,
    showBottomNavbar: MutableState<Boolean>,
    paddingValues: PaddingValues,
    paymentSheet: PaymentSheet

) {
    val user = FirebaseAuth.getInstance().currentUser
//    val startingScreen = if (user != null) AppRoutes.HomeScreen else AppRoutes.LoginScreen
    val startingScreen = AppRoutes.HomeScreen


    NavHost(
        navController = navController,
        startDestination = startingScreen,
        modifier = modifier.padding(0.dp)
    ) {

        composable<AppRoutes.Onboarding> {
            showBottomNavbar.value = false
            OnboardingScreenUI {
                navController.navigateAndClear(AppRoutes.LoginScreen)
            }
        }

        composable<AppRoutes.HomeScreen> {
            showBottomNavbar.value = true
            HomeScreenUI(navigateToCategories = {
                navController.navigateAndClear(AppRoutes.CategoryScreen)
            }, navigateToBrands = {
                navController.navigate(AppRoutes.BrandsScreen)
            }, navigateToBrand = { brand ->
                navController.navigate(AppRoutes.BrandDetailsScreen(brand.name ?: "Empty"))
            },
                navigateToCategory = { category ->
                    navController.navigate(
                        AppRoutes.CategoryDetailsScreen(
                            category.name ?: "Empty ID"
                        )
                    )
                }, navController = navController,
                snackBarHostState = snackBarHostState
            )
        }

        composable<AppRoutes.BrandsScreen> {
            showBottomNavbar.value = false
            BrandsDetailsScreenUI(navController = navController)
        }
        composable<AppRoutes.BrandDetailsScreen> {
            showBottomNavbar.value = false
            val brandArgs = it.toRoute<AppRoutes.BrandDetailsScreen>()
            BrandScreenUI(brandName = brandArgs.brandName, navController = navController)
        }

        composable<AppRoutes.ProductDetailsScreen> {
            showBottomNavbar.value = false
            val productArgs = it.toRoute<AppRoutes.ProductDetailsScreen>()
            ProductDetailsScreenUI(
                snackBarHostState,
                productId = productArgs.productId,
                navController = navController
            )
        }

        composable<AppRoutes.CategoryScreen> {
            showBottomNavbar.value = true
            CategoryScreenUI(navigateToCategory = { category ->
                navController.navigate(AppRoutes.CategoryDetailsScreen(category.name ?: "Empty ID"))
            },
                navigateToSubCategory = { category ->
                    navController.navigate(
                        AppRoutes.CategoryDetailsScreen(
                            category.name ?: "Empty ID"
                        )
                    )
                })
        }

        composable<AppRoutes.CategoryDetailsScreen> {
            showBottomNavbar.value = false
            val categoryArgs = it.toRoute<AppRoutes.CategoryDetailsScreen>()
            CategoryDetailsScreenUI(
                categoryName = categoryArgs.categoryId,
                navController = navController
            )
        }

        composable<AppRoutes.CartScreen> {
            CartScreenUI(paddingValues, paymentSheet = paymentSheet, navController = navController)
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

            PaymentScreenUI(navController, paymentSheet = paymentSheet)
        }

        composable<AppRoutes.UserOrdersScreen> {
            showBottomNavbar.value = false
            UserOrdersScreenUI(navController = navController)
        }


        composable<AppRoutes.LoginScreen> {
            showBottomNavbar.value = false
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
            AddAddressScreen(navController, lat = args.lat, lng = args.lng)
        }
        composable<AppRoutes.CreditCardDetails> {
            showBottomNavbar.value = false
            CreditCardDetailsUiLayout(navController)
        }

        composable<AppRoutes.WishListScreen> {
            showBottomNavbar.value = false
            WishListScreen(snackBarHostState, navController)
        }
        composable<AppRoutes.CurrencyScreen> {
            CurrencyScreenUi(navController)
        }
        composable<AppRoutes.MapScreen> {
            MapScreenUi(navController)
        }
        composable<AppRoutes.HelpCenterScreen> {
            showBottomNavbar.value = false
            HelpCenterScreenUiLayout(navController)
        }

        composable<AppRoutes.SearchScreen> {
            showBottomNavbar.value = false
            val args = it.toRoute<AppRoutes.SearchScreen>()

            SearchScreen(navController, snackBarHostState, isWishlist = args.isWishlist)
        }

    }
}
