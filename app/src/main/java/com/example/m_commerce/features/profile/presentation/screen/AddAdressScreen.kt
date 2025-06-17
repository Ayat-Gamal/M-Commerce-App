import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.m_commerce.R
import com.example.m_commerce.config.theme.Teal
import com.example.m_commerce.config.theme.background
import com.example.m_commerce.core.shared.components.CustomButton

@Composable
fun AddAddressScreen(backStack: () -> Unit) {
    var addressTitle by remember { mutableStateOf("") }
    var completeAddress by remember { mutableStateOf("") }
    var floor by remember { mutableStateOf("") }
    var landmark by remember { mutableStateOf("") }

    Scaffold(
        topBar = {
            Box(modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)) {
                IconButton(onClick = { backStack() }, modifier = Modifier.align(Alignment.CenterStart)) {
                    Icon(Icons.Default.ArrowBack, contentDescription = "Back", tint = Color.Black)
                }
                Text(
                    text = "Add Address",
                    modifier = Modifier.align(Alignment.Center),
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        },
        containerColor = background
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .padding(horizontal = 16.dp)
                .fillMaxSize()
        ) {
            Spacer(modifier = Modifier.height(8.dp))

            // Map Box Placeholder
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
                    .clip(RoundedCornerShape(16.dp))
                    .background(Color.LightGray),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_launcher_background),
                    contentDescription = null,
                    modifier = Modifier
                        .size(48.dp)
                        .clip(RoundedCornerShape(12.dp))
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            AddressTextField(
                label = "Address title",
                value = addressTitle,
                onValueChange = { addressTitle = it }
            )

            Spacer(modifier = Modifier.height(8.dp))

            AddressTextField(
                label = "Complete address",
                value = completeAddress,
                onValueChange = { completeAddress = it }
            )

            Spacer(modifier = Modifier.height(8.dp))

            AddressTextField(
                label = "Floor",
                value = floor,
                onValueChange = { floor = it }
            )

            Spacer(modifier = Modifier.height(8.dp))

            AddressTextField(
                label = "Landmark",
                value = landmark,
                onValueChange = { landmark = it }
            )

            Spacer(modifier = Modifier.height(24.dp))

            CustomButton(
                modifier = Modifier
                    .padding(12.dp, 12.dp)
                    .fillMaxWidth()
                    .height(50.dp),
                text = "Save Address",
                backgroundColor = Teal,
                textColor = Color.White,
                height = 50,
                cornerRadius = 12,
                onClick = { }
            )
        }
    }
}

@Composable
fun AddressTextField(label: String, value: String, onValueChange: (String) -> Unit) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        label = { Text(label) },
        modifier = Modifier
            .fillMaxWidth(),
        shape = RoundedCornerShape(12.dp)
    )
}
