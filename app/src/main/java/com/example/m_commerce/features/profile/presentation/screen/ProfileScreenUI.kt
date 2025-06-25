package com.example.m_commerce.features.profile.presentation.screen

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CreditCard
import androidx.compose.material.icons.filled.CurrencyExchange
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.Gray
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.m_commerce.config.routes.AppRoutes
import com.example.m_commerce.config.theme.Background
import com.example.m_commerce.core.shared.components.NetworkImage
import com.example.m_commerce.core.shared.components.default_top_bar.DefaultTopBar
import com.example.m_commerce.features.profile.domain.entity.ProfileOption
import com.example.m_commerce.features.profile.presentation.components.profile.ProfileOptionsList
import com.example.m_commerce.features.profile.presentation.viewmodel.ProfileViewModel
import com.google.firebase.auth.FirebaseAuth


@Composable
fun ProfileScreenUI(
    navController: NavHostController,
    viewModel: ProfileViewModel = viewModel(),
    modifier: Modifier = Modifier
) {
//    val uiState = viewModel.profileState.collectAsState().value
    ProfileContent(navController)


//    when (uiState) {
//        is ProfileUiState.Loading -> {
//            // Show loading spinner
//            Text("Loading...")
//        }
//
//        is ProfileUiState.Guest -> {
//            //Text("You're browsing as a guest. Please log in.")
//        }
//
//        is ProfileUiState.NoNetwork -> {
//            Text("No internet connection. Please try again later.")
//        }
//
//        is ProfileUiState.Error -> {
//            // Show error message
//            Text("Error: ${uiState.error}")
//        }
//
//        is ProfileUiState.Success -> {
//
//            ProfileContent(
//                navController, ProfileUiState.Success(
//                    profileName = "Mohamed",
//                    profileImageUrl = "https://cdn.example.com/image.jpg"
//                )
//            )
//        }
//
//        ProfileUiState.Empty -> {
//            Text("No data found.")
//        }
//    }

}


@SuppressLint("StateFlowValueCalledInComposition")
@Composable
fun ProfileContent(navController: NavHostController/*, profileuistate: ProfileUiState.Success*/) {

    val options = listOf(
        ProfileOption("Your profile", Icons.Default.Person),
        ProfileOption("Manage Address", Icons.Default.LocationOn),
        ProfileOption("Payment Methods", Icons.Default.CreditCard),
        ProfileOption("My Orders", Icons.Default.ShoppingCart),
        ProfileOption("My Wishlist", Icons.Default.Favorite),
        ProfileOption("Currency", Icons.Default.CurrencyExchange),
        ProfileOption("Settings", Icons.Default.Settings),
        ProfileOption("Help Center", Icons.Default.LocationOn),
    )


    Scaffold(topBar = {
        DefaultTopBar(title = "Profile ", navController = null)
    }) { padding ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Background)
                .padding(16.dp)
        ) {
            Column(
                modifier = Modifier.padding(padding),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Box(
                    modifier = Modifier.fillMaxWidth(),
                    contentAlignment = Alignment.Center
                ) {
                    NetworkImage(
                        url = (FirebaseAuth.getInstance().currentUser?.photoUrl ?: "https://images-ext-1.discordapp.net/external/7VlXibo_9bX2x9sVjwk_f6vnZxdJveVCOPfiohEzkho/https/i.pinimg.com/736x/17/c0/d1/17c0d1bfcef18ad4a83d5b5b95f328df.jpg?format=webp&width=920&height=920").toString(),
                        modifier = Modifier
                            .size(100.dp)
                            .clip(CircleShape)
                            .clip(CircleShape)
                            .border(2.dp, Gray, CircleShape)
                    )

                }

                Spacer(modifier = Modifier.height(8.dp))


                Text(
                    "Welcome ${(FirebaseAuth.getInstance().currentUser?.displayName ?: "Guest")}"
                )
            }

            Spacer(modifier = Modifier.height(16.dp))


            ProfileOptionsList(items = options) { option ->
                when (option.title) {
                    "Manage Address" -> navController.navigate(AppRoutes.ManageAddressScreen)
//                "Your profile" -> navController.navigate("your_profile")
                    "Payment Methods" -> navController.navigate(AppRoutes.PaymentScreen)
                    "My Orders" -> navController.navigate(AppRoutes.UserOrdersScreen)
                    "My Wishlist" -> navController.navigate(AppRoutes.WishListScreen)
                    //"My Coupons" -> navController.navigate("my_coupons")
                    "Currency" -> navController.navigate(AppRoutes.CurrencyScreen)
                    "Help Center" -> navController.navigate(AppRoutes.HelpCenterScreen)
                }
            }
            if (FirebaseAuth.getInstance().currentUser != null) {
                Text(
                    text = "Logout",
                    color = Color.Red,
                    style = TextStyle(
                        textDecoration = TextDecoration.Underline,
                        fontWeight = FontWeight.Bold
                    ),
                    modifier = Modifier
                        .clickable(onClick = {
                            FirebaseAuth.getInstance().signOut()
                            navController.navigate(AppRoutes.LoginScreen) {
                                popUpTo(AppRoutes.ProfileScreen) { inclusive = true }
                            }

                        })
                        .padding(16.dp)
                )
            }
        }
    }
}
