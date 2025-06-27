package com.example.m_commerce.features.onboarding.presentation.screen

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.m_commerce.R
import com.example.m_commerce.core.shared.components.CustomButton
import kotlinx.coroutines.launch

@SuppressLint("RememberReturnType")
@Composable
fun OnboardingScreenUI(
    modifier: Modifier = Modifier,
    onNavigateToLogin: () -> Unit,
) {
    val pages = listOf(
        R.drawable.ic_launcher_background,
        R.drawable.ic_launcher_background,
        R.drawable.ic_launcher_background,
        R.drawable.ic_launcher_background,
    )

    val animationScope = rememberCoroutineScope()
    val pagerState = rememberPagerState(
        initialPage = 0,
        initialPageOffsetFraction = 0f,
        pageCount = { pages.size }
    )

    val currentPage = pagerState.currentPage

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceEvenly
    ) {
        HorizontalPager(
            state = pagerState,
            modifier = Modifier
                .fillMaxWidth()
                .height(400.dp),
            contentPadding = PaddingValues(horizontal = 24.dp),
            pageSpacing =  24.dp
        ) { page ->
            Image(
                painter = painterResource(id = pages[page]),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .clip(RoundedCornerShape(32.dp))
                    .fillMaxSize()
            )

            Spacer(modifier = Modifier.height(24.dp))
        }

        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                text = "Various Collections Of The\nLatest Products",
                style = MaterialTheme.typography.titleMedium,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "Urna amet, suspendisse ullamcorper ac elit diam\nfacilisis cursus vestibulum.",
                style = MaterialTheme.typography.bodyMedium.copy(color = Color.Gray),
                textAlign = TextAlign.Center
            )
        }
        Spacer(modifier = Modifier.height(24.dp))

        CustomButton(
            modifier = Modifier.padding(horizontal = 24.dp),
            text = if (currentPage == pages.size - 1) "Get Started" else "Next"
        ) {
            animationScope.launch {
                if (currentPage < pages.size - 1) {
                    pagerState.animateScrollToPage(currentPage + 1)
                } else {
                    onNavigateToLogin()
                }
            }
        }
    }
}
