package com.example.m_commerce.core.shared.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.m_commerce.config.routes.AppRoutes
import com.example.m_commerce.config.theme.Gray

@Composable
fun GuestMode(
    navController: NavHostController,
    name: String,
    imageVector: ImageVector
) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .size(72.dp)
                .background(Color.LightGray, RoundedCornerShape(50)),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                modifier = Modifier.size(48.dp),
                imageVector = imageVector,
                contentDescription = "",
                tint = Color.Gray
            )
        }

        Spacer(Modifier.height(16.dp))
        Text(
            "Your $name",
            fontWeight = FontWeight.Bold,
            style = MaterialTheme.typography.headlineSmall
        )

        Spacer(Modifier.height(24.dp))
        Text(
            "You need to login to create and see your $name items",
            color = Gray,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp),
            textAlign = TextAlign.Center
        )

        Spacer(Modifier.height(24.dp))
        CustomButton(
            modifier = Modifier.padding(horizontal = 24.dp),
            text = "Sign in",
            onClick = {
                navController.navigate(AppRoutes.LoginScreen)
            })

        Spacer(Modifier.height(24.dp))
        CustomButton(
            modifier = Modifier.padding(horizontal = 24.dp),
            text = "Create Account",
            onClick = {
                navController.navigate(AppRoutes.RegisterScreen)
            })
    }
}