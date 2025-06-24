
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.ClipboardManager
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.m_commerce.config.theme.Teal
import com.example.m_commerce.config.theme.TextBackground

@Composable
fun SpecialOfferCard(
    modifier: Modifier = Modifier,
    couponCodes: List<String>
) {
    val pagerState = rememberPagerState(initialPage = 0, pageCount = { couponCodes.size })
    val clipboardManager: ClipboardManager = LocalClipboardManager.current
    val context = LocalContext.current

    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 0.dp, vertical = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        HorizontalPager(
            state = pagerState,
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
        ) { page ->
            val code = couponCodes[page]

            Card(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 16.dp)
                    .combinedClickable(
                        onClick = {},
                        onLongClick = {
                            clipboardManager.setText(androidx.compose.ui.text.AnnotatedString(code))
                            Toast.makeText(context, "Code copied: $code", Toast.LENGTH_SHORT).show()
                        }
                    ),
                colors = CardDefaults.cardColors(containerColor = TextBackground),
                elevation = CardDefaults.cardElevation(6.dp)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(24.dp),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "Special Offer!",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        color = Teal
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                    Text(
                        text = code,
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Medium,
                        color = Teal
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "Long-press to copy",
                        fontSize = 14.sp,
                        color = Teal.copy(alpha = 0.7f)
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
                val color = if (isSelected) Teal else Color.LightGray

                Box(
                    modifier = Modifier
                        .padding(horizontal = 4.dp)
                        .height(8.dp)
                        .width(8.dp)
                        .background(color = color, shape = CircleShape)
                )
            }
        }
    }
}
