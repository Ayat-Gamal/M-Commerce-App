package com.example.m_commerce.features.profile.presentation.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.m_commerce.R



//background: #f6f6f6;
@Composable
fun ProfileScreenUI(modifier: Modifier = Modifier) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp)
        ) {
            IconButton(
                onClick = { /* handle back */ },
                modifier = Modifier.align(Alignment.CenterStart)
            ) {
                Icon(Icons.Default.ArrowBack, contentDescription = "Back")
            }

            Text(
                text = "Profile",
                style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold),
                modifier = Modifier.align(Alignment.Center)
            )
        }

        Spacer(modifier = Modifier.height(8.dp))

        Column(horizontalAlignment = Alignment.CenterHorizontally) {
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

            Text("Profile name", style = MaterialTheme.typography.titleMedium)
        }

        Spacer(modifier = Modifier.height(16.dp))

        val options = remember {
            listOf(
                ProfileOption("Your profile", Icons.Default.Person),
                ProfileOption("Manage Address", Icons.Default.LocationOn),
                ProfileOption("Payment Methods", Icons.Filled.Done),
                ProfileOption("My Orders", Icons.Default.ShoppingCart),
                ProfileOption("My Wishlist", Icons.Default.Favorite),
                ProfileOption("My Coupons", Icons.Default.LocationOn),
                ProfileOption("Settings", Icons.Default.Settings),
                ProfileOption("Help Center", Icons.Default.LocationOn)
            )
        }

        ProfileOptionsList(items = options) { option ->
            // Handle click here
        }
    }
}

@Composable
fun ProfileOptionsList(
    items: List<ProfileOption>,
    modifier: Modifier = Modifier,
    onItemClick: (ProfileOption) -> Unit = {}
) {
    LazyColumn(modifier = modifier.fillMaxWidth()) {
        itemsIndexed(items) { index, option ->
            ProfileOptionItem(option = option, onClick = { onItemClick(option) })
            if (index < items.lastIndex) {
                Divider(color = MaterialTheme.colorScheme.outlineVariant, thickness = 1.dp)
            }
        }
    }
}

@Composable
fun ProfileOptionItem(option: ProfileOption, onClick: () -> Unit = {}) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .padding(vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(option.icon, contentDescription = option.title, modifier = Modifier.size(24.dp))
        Spacer(modifier = Modifier.width(16.dp))
        Text(option.title, modifier = Modifier.weight(1f))
        Icon(
            Icons.Default.KeyboardArrowRight,
            contentDescription = "Go",
            modifier = Modifier.size(20.dp)
        )
    }
}

data class ProfileOption(val title: String, val icon: ImageVector)

@Preview(showBackground = true)
@Composable
private fun ProfileScreenUIPreview() {
    ProfileScreenUI()
}
