
import androidx.compose.foundation.background
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ContentCopy
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.ClipboardManager
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import com.example.m_commerce.config.theme.Black
import com.example.m_commerce.config.theme.Teal
import com.example.m_commerce.config.theme.TextBackground
import com.example.m_commerce.config.theme.White
import com.example.m_commerce.features.coupon.domain.entity.Coupon
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch

@Composable
fun SpecialOfferCard(
    modifier: Modifier = Modifier,
    couponCodes: List<Coupon>,
    snackBarHostState: SnackbarHostState,
) {
    val pagerState = rememberPagerState(initialPage = 0, pageCount = { couponCodes.size })
    val clipboardManager: ClipboardManager = LocalClipboardManager.current

    val images = listOf(
        "https://i.pinimg.com/736x/68/83/df/6883dfb9c2db84767bbf0a4c224ce3d0.jpg",
        "https://i.pinimg.com/736x/d6/ef/49/d6ef490d4caccc2d0bd3904991be4fa6.jpg",
        "https://i.pinimg.com/736x/48/d8/db/48d8db9ce3074525c652fe725813e6ed.jpg"
    ,"https://i.pinimg.com/736x/c5/16/93/c51693e58bac33aaadec080b262831a0.jpg",
        "https://i.pinimg.com/736x/c5/03/bc/c503bcf57dfb96e418e96527bb961bd1.jpg"
    )

    LaunchedEffect(Unit) {
        while (isActive) {
            delay(4000)
            if (couponCodes.isNotEmpty()) {
                val nextPage = (pagerState.currentPage + 1) % couponCodes.size
                pagerState.animateScrollToPage(nextPage)
            }
        }
    }

    Column(
        modifier = modifier.fillMaxWidth().padding(vertical = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        HorizontalPager(
            state = pagerState,
            modifier = Modifier.fillMaxWidth().height(180.dp)
        ) { page ->
            val coupon = couponCodes[page]
            val imageUrl = images[page % images.size]

            Card(
                modifier = Modifier
                    .padding(horizontal = 16.dp)
                    .fillMaxSize(),
                shape = RoundedCornerShape(16.dp),
                elevation = CardDefaults.cardElevation(8.dp),
                colors = CardDefaults.cardColors(containerColor = White)
            ) {
                Row(
                    modifier = Modifier.fillMaxSize().padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column(
                        modifier = Modifier.weight(1f)
                    ) {
                        Text("Offers and Discounts", fontSize = 10.sp, color = Black)
                        Text(
                            text = "Get Special Offer",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold,
                            color = Black
                        )
                        Text(
                            text = "Up to ${coupon.summary}",
                            fontSize = 14.sp,
                            color = Teal
                        )
                        Spacer(modifier = Modifier.height(12.dp))

                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier
                                .clip(RoundedCornerShape(50))
                                .combinedClickable(
                                    onClick = {
                                        clipboardManager.setText(AnnotatedString(coupon.code))
                                        CoroutineScope(Dispatchers.Main).launch {
                                            snackBarHostState.showSnackbar("Copied: ${coupon.code}")
                                        }
                                    },
                                    onLongClick = {}
                                )
                                .background(Teal)
                                .padding(horizontal = 12.dp, vertical = 6.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.ContentCopy,
                                contentDescription = "Copy",
                                tint = White,
                                modifier = Modifier.size(16.dp)
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = coupon.code,
                                fontSize = 14.sp,
                                color = White,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }

                    Spacer(modifier = Modifier.width(16.dp))

                    AsyncImage(
                        model = imageUrl,
                        contentDescription = null,
                        modifier = Modifier
                            .fillMaxHeight()
                            .aspectRatio(1f)
                            .clip(RoundedCornerShape(12.dp))
                    )
                }
            }
        }

        Row(
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 12.dp)
        ) {
            repeat(couponCodes.size) { index ->
                val isSelected = pagerState.currentPage == index
                Box(
                    modifier = Modifier
                        .padding(horizontal = 4.dp)
                        .size(8.dp)
                        .clip(CircleShape)
                        .background(if (isSelected) Teal else TextBackground)
                )
            }
        }
    }
}



