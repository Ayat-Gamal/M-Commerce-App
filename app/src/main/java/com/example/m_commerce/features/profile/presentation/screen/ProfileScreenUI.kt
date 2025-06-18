package com.example.m_commerce.features.profile.presentation.screen

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.m_commerce.R
import com.example.m_commerce.config.routes.AppRoutes
import com.example.m_commerce.config.theme.Background
import com.example.m_commerce.core.shared.components.default_top_bar.DefaultTopBar
import com.example.m_commerce.features.profile.presentation.components.ProfileOptionsList
import com.example.m_commerce.features.profile.presentation.viewmodel.ProfileViewModel

@SuppressLint("StateFlowValueCalledInComposition")
@Composable
fun ProfileScreenUI(
    navController: NavHostController,
    viewModel: ProfileViewModel = viewModel(),
    modifier: Modifier = Modifier
) {
    val state by viewModel.profileState.collectAsStateWithLifecycle()

    Scaffold(topBar = {
        DefaultTopBar(title = "Profile", navController = null)

    }) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Background)
                .padding(16.dp)
        ) {
//        Box(
//            modifier = Modifier
//                .fillMaxWidth()
//                .padding(top = 16.dp)
//        ) {
//            Text(
//                text = "Profile",
//                modifier = Modifier.align(Alignment.Center)
//            )
//        }

//        Spacer(modifier = Modifier.height(39.dp))

            Column(modifier = Modifier.padding(padding), horizontalAlignment = Alignment.CenterHorizontally) {
                Box(
                    modifier = Modifier.fillMaxWidth(),
                    contentAlignment = Alignment.Center
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.ic_launcher_foreground),
                        contentDescription = "Profile photo",
                        modifier = Modifier
                            .size(100.dp)
                            .clip(CircleShape)
                            .border(2.dp, Color.Gray, CircleShape),
                        contentScale = ContentScale.Crop
                    )
                }

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    "Profile name"
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            val options = viewModel.profileState.value.profileOptions

            ProfileOptionsList(items = options) { option ->
                when (option.title) {
                    "Manage Address" -> navController.navigate(AppRoutes.ManageAddressScreen)
//                "Your profile" -> navController.navigate("your_profile")
//                "Payment Methods" -> navController.navigate("payment_methods")
//                "My Orders" -> navController.navigate("my_orders")
//                "My Wishlist" -> navController.navigate("my_wishlist")
//                "My Coupons" -> navController.navigate("my_coupons")
//                "Settings" -> navController.navigate("settings")
//                "Help Center" -> navController.navigate("help_center")
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun ProfileScreenUIPreview() {
    ProfileScreenUI(navController = rememberNavController())
}
