
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.m_commerce.config.routes.AppRoutes
import com.example.m_commerce.config.theme.Background
import com.example.m_commerce.config.theme.Teal
import com.example.m_commerce.config.theme.White
import com.example.m_commerce.core.shared.components.CustomButton
import com.example.m_commerce.core.shared.components.default_top_bar.DefaultTopBar
import com.example.m_commerce.features.profile.presentation.components.AddNewAddressButton
import com.example.m_commerce.features.profile.presentation.components.AddressCard
import com.example.m_commerce.features.profile.presentation.viewmodel.MangeAddressViewModel


@Composable
fun ManageAddressScreenUi(
    navController: NavHostController,
    viewModel: MangeAddressViewModel = MangeAddressViewModel()
) {
    val scrollState = rememberScrollState()
    Scaffold(topBar = {
        DefaultTopBar(title = "Manage Address", navController = navController)

    }) {
            padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Background)
//                .verticalScroll(scrollState)
                .padding(padding)
        ) {
            Text(text="Set default address ", fontWeight = FontWeight.Bold, modifier = Modifier.padding(start = 16.dp))
            viewModel.addresses.forEachIndexed { index, item ->
                AddressCard(
                    item = item,
                    isSelected = viewModel.selectedIndex.value == index,
                    onSelect = { viewModel.selectedIndex.value = index },
                    onDelete = { print("Deleted")}
                )
                Spacer(modifier = Modifier.height(8.dp))
            }

            AddNewAddressButton(onClick = {
                navController.navigate(AppRoutes.AddAddressScreen)
            })

            Spacer(modifier = Modifier.weight(1f))

            CustomButton(
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth()
                    .height(50.dp),
                text = "Apply",
                backgroundColor = Teal,
                textColor = White,
                height = 50,
                cornerRadius = 12,
                onClick = { }
            )
            Spacer(modifier = Modifier.height(32.dp))
        }

    }
}


@Preview(showBackground = true)
@Composable
fun ShippingAddressScreenPreview() {
    val navController = rememberNavController()
    ManageAddressScreenUi(navController = navController)
}