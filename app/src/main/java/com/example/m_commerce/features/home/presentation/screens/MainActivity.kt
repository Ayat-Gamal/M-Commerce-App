package com.example.m_commerce.features.home.presentation.screens

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.m_commerce.BuildConfig
import com.example.m_commerce.config.routes.NavSetup
import com.example.m_commerce.config.theme.MCommerceTheme
import com.example.m_commerce.core.shared.components.bottom_nav_bar.BottomNavBar
import com.example.m_commerce.features.orders.data.model.variables.LineItem
import com.stripe.android.PaymentConfiguration
import com.stripe.android.paymentsheet.PaymentSheet
import com.stripe.android.paymentsheet.PaymentSheetResult
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private lateinit var showBottomNavbar: MutableState<Boolean>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        PaymentConfiguration.init(applicationContext, BuildConfig.PAYMENT_PUBLISHABLE_KEY)

        enableEdgeToEdge()
        setContent {
            MCommerceTheme {
                showBottomNavbar = remember { mutableStateOf(false) }
                MainLayout(
                    showBottomNavbar = showBottomNavbar,
//                    paymentSheet = paymentSheet
                )
            }
        }
    }

    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    @Composable
    fun MainLayout(
        navController: NavHostController = rememberNavController(),
        showBottomNavbar: MutableState<Boolean>,
        paymentSheet: PaymentSheet? = null
    ) {

        val snackBarHostState = remember { SnackbarHostState() }
        Scaffold(
            snackbarHost = {
                SnackbarHost(hostState = snackBarHostState)
            },
            bottomBar = {
                if (showBottomNavbar.value) {
                    BottomNavBar(navController)
                }
            }
        ) { innerPadding ->
            val noBottomPadding = Modifier.padding(
                start = innerPadding.calculateStartPadding(LayoutDirection.Ltr),
                top = innerPadding.calculateTopPadding(),
                end = innerPadding.calculateEndPadding(LayoutDirection.Ltr),
                bottom = 16.dp
            )
            NavSetup(
                navController,
                snackBarHostState,
                modifier = noBottomPadding,
                showBottomNavbar,
                paddingValues = innerPadding,
//                paymentSheet = paymentSheet
            )
        }
    }

}

private fun onPaymentResult(result: PaymentSheetResult, items: List<LineItem>) {
    when (result) {
        is PaymentSheetResult.Completed -> {


            println("✅ Payment completed")
        }

        is PaymentSheetResult.Canceled -> {
            println("⚠️ Payment canceled")
        }

        is PaymentSheetResult.Failed -> {
            println("❌ Payment failed: ${result.error.localizedMessage}")
        }
    }
}

