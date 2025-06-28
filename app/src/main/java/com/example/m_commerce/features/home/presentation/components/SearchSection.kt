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
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.m_commerce.R
import com.example.m_commerce.config.routes.AppRoutes
import com.example.m_commerce.core.shared.components.SvgButton
import com.example.m_commerce.core.shared.components.SvgImage
import com.example.m_commerce.features.product.presentation.screen.capitalizeEachWord


@Composable
fun SearchSection(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    userName: String
) {

    Row(Modifier.fillMaxWidth().padding(start = 16.dp, end = 16.dp, top = 16.dp), horizontalArrangement = Arrangement.SpaceBetween) {
        Column {
            Text("Hi, ${userName.capitalizeEachWord()}", fontSize = 18.sp, fontWeight = FontWeight.Bold)
            Text("Let's go shopping", fontSize = 12.sp, fontStyle = FontStyle.Italic)
        }
        SvgButton(R.raw.search) {
            navController.navigate(AppRoutes.SearchScreen(false))
        }
    }
}