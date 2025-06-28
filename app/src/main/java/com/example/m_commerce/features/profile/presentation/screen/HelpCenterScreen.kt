package com.example.m_commerce.features.profile.presentation.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Facebook
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil3.compose.AsyncImage
import com.example.m_commerce.R
import com.example.m_commerce.config.theme.Background
import com.example.m_commerce.config.theme.TextBackground
import com.example.m_commerce.core.shared.components.default_top_bar.DefaultTopBar
import com.example.m_commerce.features.profile.domain.entity.Person


val teamMembers = listOf(
    Person(
        name = "Ahmed Mohamed Saad ",
        title = "Mobile Software Engineer",
        imageUrl = "https://avatars.githubusercontent.com/u/193324840?v=4",
        githubUrl = "https://github.com/ahmedsaad207",
        linkedinUrl = "https://www.linkedin.com/in/dev-ahmed-saad/",
        facebookUrl = ""
    ),
    Person(
        name = "Ayat Gamal Mustafa",
        title = "Mobile Software Engineer",
        imageUrl = "https://avatars.githubusercontent.com/u/90482904?v=4",
        githubUrl = "https://github.com/ahmedsaad207",
        linkedinUrl = "https://www.linkedin.com/in/ayat-gamal-700946229/",
        facebookUrl = ""
    ),
    Person(
        name = "Mohamed Tag El-Deen Ahmed",
        title = "Mobile Software Engineer",
        imageUrl = "https://lh3.googleusercontent.com/a/ACg8ocI6yXCBJnS3J-O78civXQxaWTXVnc0zW4ti1iBBmQTjVGrMHRS3=s360-c-no",
        githubUrl = "",
        linkedinUrl = "https://www.linkedin.com/in/mohamed-tag-eldeen",
        facebookUrl = ""
    ),
    Person(
        name = "Youssif Nasser Mostafa ",
        title = "Mobile Software Engineer",
        imageUrl = "https://avatars.githubusercontent.com/u/72336910?v=4",
        githubUrl = "https://github.com/JoeTP",
        linkedinUrl = "https://www.linkedin.com/in/youssif-nasser/",
        facebookUrl = ""
    ),
)

@Composable
fun HelpCenterScreenUiLayout(navController: NavHostController) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Background)
    ) {
        Scaffold(
            topBar = {
                DefaultTopBar(title = "Help Center", navController = navController)
            },
            containerColor = Color.Transparent
        ) { innerPadding ->
            LazyColumn(
                modifier = Modifier
                    .padding(innerPadding)
                    .padding(horizontal = 16.dp, vertical = 8.dp)
            ) {
                items(teamMembers) { person ->
                    PersonCard(person)
                }
            }
        }
    }
}

@Composable
fun PersonCard(person: Person) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp) ,
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(4.dp),
        colors = CardDefaults.cardColors(containerColor = TextBackground)
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            AsyncImage(
                model = person.imageUrl,
                contentDescription = person.name,
                modifier = Modifier
                    .size(66.dp)
                    .clip(CircleShape),
                contentScale = ContentScale.Crop
            )

            Spacer(modifier = Modifier.width(12.dp))

            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(text = person.name, fontWeight = FontWeight.Bold, fontSize = 16.sp)
                Text(text = person.title, color = Color.Gray, fontSize = 13.sp)

                Row(verticalAlignment = Alignment.CenterVertically) {
                    IconButton(onClick = { launchUrl(person.githubUrl) }) {
                        Icon(painter = painterResource(id = R.drawable.gihub), contentDescription = "GitHub")
                    }
                    IconButton(onClick = { launchUrl(person.linkedinUrl) }) {
                        Icon(painter = painterResource(id = R.drawable.linkedin), contentDescription = "LinkedIn")
                    }
                    IconButton(onClick = { launchUrl(person.facebookUrl) }) {
                        Icon(imageVector = Icons.Default.Facebook, contentDescription = "Facebook")
                    }
                }
            }
        }
    }
}

fun launchUrl(url: String) {
    println("Launching URL: $url")
}
