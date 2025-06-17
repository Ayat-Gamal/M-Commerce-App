package com.example.m_commerce.features.home.presentation.screens

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.m_commerce.config.routes.NavSetup
import com.example.m_commerce.config.theme.MCommerceTheme
import com.example.m_commerce.core.shared.components.bottom_nav_bar.BottomNavBar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    lateinit var showBottomNavbar: MutableState<Boolean>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MCommerceTheme {
                showBottomNavbar = remember { mutableStateOf(false) }
                MainLayout(showBottomNavbar = showBottomNavbar)
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Column(Modifier.fillMaxSize(), verticalArrangement = Arrangement.Center) {
        Text(
            text = "Hello $name!",
        )
    }
}

@Composable
fun MainLayout(navController: NavHostController = rememberNavController(), showBottomNavbar: MutableState<Boolean>) {
    val snackbarHostState = remember { SnackbarHostState() }
    Scaffold(
        snackbarHost = {
            SnackbarHost(hostState = snackbarHostState)
        },
        bottomBar = { if (showBottomNavbar.value)  { BottomNavBar(navController) } else null }
    ) { innerPadding  ->
        NavSetup(navController, snackbarHostState, modifier = Modifier.padding(innerPadding),showBottomNavbar)
    }
}




