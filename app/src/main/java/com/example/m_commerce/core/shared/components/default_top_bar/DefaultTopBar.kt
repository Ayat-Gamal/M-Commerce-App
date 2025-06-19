package com.example.m_commerce.core.shared.components.default_top_bar

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavHostController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DefaultTopBar(
    modifier: Modifier = Modifier,
    title: String,
    navController: NavHostController?,
    titleCentered: Boolean = false
) {
    TopAppBar(
        modifier = modifier,
        title = {
            Box(
                modifier = Modifier.fillMaxWidth(),
                contentAlignment = if (titleCentered) Alignment.Center else Alignment.CenterStart
            ) {
                Text(title)
            }
        },
        navigationIcon = {
            if (navController != null) BackButton(navController)
        },
        colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.Transparent)
    )}
//fun DefaultTopBar(modifier: Modifier = Modifier, title: String, navController : NavHostController?) {
//    TopAppBar(title =  { Text(title)}, navigationIcon = {
//        if (navController != null) BackButton(navController)
//    }, colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.Transparent))
//}
/* <<<<<<<<<<  16473226-8c9f-48f3-affa-1d78bfc8e116  >>>>>>>>>>> */

