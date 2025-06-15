// BottomNavBar.kt
package com.example.m_commerce.core.shared.components.bottom_nav_bar

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.m_commerce.config.theme.DarkestGray
import com.example.m_commerce.config.theme.Gray
import com.example.m_commerce.config.theme.Teal
import com.example.m_commerce.config.theme.White
import com.example.m_commerce.core.shared.components.SvgImage

@Composable
fun BottomNavBar(navController: NavHostController) {
    val items = listOf(
        BottomNavItem.Home,
        BottomNavItem.Category,
        BottomNavItem.Cart,
        BottomNavItem.Profile
    )

    val currentDestination = navController.currentBackStackEntryAsState().value?.destination?.route

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 16.dp, horizontal = 32.dp),
        contentAlignment = Alignment.Center
    ) {
        Row(
            modifier = Modifier
                .background(
                    color = DarkestGray,
                    shape = RoundedCornerShape(50)
                )
                .padding(12.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            items.forEach { item ->

                val isSelected = currentDestination?.contains(item.route::class.simpleName.toString()) ?: false
                BnbIcon(
                    item = item,
                    isSelected = isSelected,
                    navController = navController
                )
            }
        }
    }
}


@Composable
fun BnbIcon(item: BottomNavItem, isSelected: Boolean, navController: NavHostController) {
    Box(
        modifier = Modifier
            .size(48.dp)
            .clip(CircleShape)
            .background(if (isSelected) White else Color.Transparent)
            .clickable {
                navController.navigate(item.route)
            },
        contentAlignment = Alignment.Center
    ) {
        SvgImage(
            resId = item.iconRes,
            contentDescription = item.label,
            modifier = Modifier.size(24.dp),
            colorFilter = ColorFilter.tint(if (isSelected) Teal else Gray)
        )
//        Icon(
//            imageVector = item.icon,
//            contentDescription = item.label,
//            tint = if (isSelected) Teal else Gray
//        )
    }
}