package com.example.m_commerce.features.home.presentation.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.m_commerce.R
import com.example.m_commerce.config.routes.AppRoutes
import com.example.m_commerce.core.shared.components.SvgButton
import com.example.m_commerce.core.shared.components.SvgImage


@Composable
fun SearchSection(
    modifier: Modifier = Modifier,
    navController: NavHostController,
) {

    Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
        Column {
            Text("Hi, User")
            Text("Let\'s go shopping")
        }
        SvgButton(R.raw.search) {
            navController.navigate(AppRoutes.SearchScreen(false))
        }
    }
}