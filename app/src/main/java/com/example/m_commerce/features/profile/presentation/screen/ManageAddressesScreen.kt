import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.m_commerce.config.routes.AppRoutes
import com.example.m_commerce.config.theme.Teal
import com.example.m_commerce.config.theme.background
import com.example.m_commerce.core.shared.components.CustomButton
import com.example.m_commerce.features.profile.presentation.components.AddNewAddressButton
import com.example.m_commerce.features.profile.presentation.components.AddressCard
import com.example.m_commerce.features.profile.presentation.components.TopBar
import com.example.m_commerce.features.profile.presentation.viewmodel.MangeAddressViewModel


@Composable
fun ManageAddressScreen(
    navController: NavHostController,
    viewModel: MangeAddressViewModel = MangeAddressViewModel()
) {
    Scaffold(
        topBar = {
            TopBar(title = "Manage Address") {
                navController.popBackStack()
            }
        },
        content = { paddingValues ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .background(background)
            ) {
                Spacer(modifier = Modifier.height(16.dp))

                viewModel.addresses.forEachIndexed { index, item ->
                    AddressCard(
                        item = item,
                        isSelected = viewModel.selectedIndex.value == index,
                        onSelect = { viewModel.selectedIndex.value = index }
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
                    textColor = Color.White,
                    height = 50,
                    cornerRadius = 12,
                    onClick = { }
                )
            }
        }
    )
}


//@Composable
//fun ManageAddressScreen(navController: NavHostController) {
//    val addresses = listOf(
//        AddressItem("Home", "1901 Thornridge Cir. Shiloh, Hawaii 81063"),
//        AddressItem("Office", "4517 Washington Ave. Manchester, Kentucky 39495"),
//        AddressItem("Parent's House", "8502 Preston Rd. Inglewood, Maine 98380"),
//        AddressItem("Friend's House", "2484 Royal Ln. Mesa, New Jersey 45463")
//    )
//
//    var selectedIndex by remember { mutableStateOf(0) }
//
//    Column(
//        modifier = Modifier
//            .fillMaxSize()
//            .background(background)
//    ) {
//        Box(modifier = Modifier.fillMaxWidth()) {
//            IconButton(
//                onClick = { navController.popBackStack() },
//                modifier = Modifier.align(Alignment.CenterStart)
//            ) {
//                Icon(Icons.Default.ArrowBack, contentDescription = "Back", tint = Color.Black)
//            }
//            Text(
//                text = "Manage Address",
//                fontFamily = interFontFamily,
//                fontWeight = FontWeight.Bold,
//                fontSize = 17.3.sp,
//                modifier = Modifier.align(Alignment.Center)
//            )
//        }
//
//        Spacer(modifier = Modifier.height(16.dp))
//
//        addresses.forEachIndexed { index, item ->
//            AddressCard(
//                item = item,
//                isSelected = selectedIndex == index,
//                onSelect = { selectedIndex = index }
//            )
//            Spacer(modifier = Modifier.height(8.dp))
//        }
//
//        AddNewAddressButton(onClick = { navController.navigate(AppRoutes.AddAddressScreen) })
//
//        Spacer(modifier = Modifier.weight(1f))
//
////        ApplyButton(
////            modifier = Modifier
////                .padding(16.dp, 16.dp)
////        )
//        CustomButton(
//            modifier = Modifier
//                .padding(16.dp, 16.dp)
//                .fillMaxWidth()
//                .height(50.dp),
//            text = "Apply",
//            backgroundColor = Teal,
//            textColor = Color.White,
//            height = 50,
//            cornerRadius = 12,
//            onClick = { }
//        )
//
//    }
//}


@Preview(showBackground = true)
@Composable
fun ShippingAddressScreenPreview() {
    val navController = rememberNavController()
    ManageAddressScreen(navController = navController)
}