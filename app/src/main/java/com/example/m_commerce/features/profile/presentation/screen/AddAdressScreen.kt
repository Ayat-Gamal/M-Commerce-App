
import android.content.Context
import android.location.Geocoder
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.m_commerce.R
import com.example.m_commerce.config.theme.Background
import com.example.m_commerce.config.theme.Teal
import com.example.m_commerce.config.theme.White
import com.example.m_commerce.core.shared.components.CustomButton
import com.example.m_commerce.core.shared.components.default_top_bar.DefaultTopBar
import com.shopify.buy3.GraphCall
import com.shopify.buy3.GraphClient
import com.shopify.buy3.GraphError
import com.shopify.buy3.GraphResponse
import com.shopify.buy3.Storefront
import org.chromium.base.Callback
import java.util.Locale


@Composable
fun AddAddressScreen(navController: NavHostController, lat: Double, lng: Double) {
    var addressType by remember { mutableStateOf("Home") }
    var completeAddress by remember { mutableStateOf("") }
    var floor by remember { mutableStateOf("") }
    var landmark by remember { mutableStateOf("") }
    var title by remember { mutableStateOf("") }
    val localContext = LocalContext.current
    LaunchedEffect(Unit) {
        val geocoder = Geocoder(localContext, Locale.getDefault())
        val addressList = geocoder.getFromLocation(lat, lng, 1)
        if (!addressList.isNullOrEmpty()) {
            val address = addressList[0]
            completeAddress = address.getAddressLine(0) ?: ""
        }
    }
    Scaffold(
        topBar = {
            DefaultTopBar(title = "Add Address", navController = navController)
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Background)
                .padding(horizontal = 16.dp)
                .padding(padding)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
                    .clip(RoundedCornerShape(16.dp))
                    .background(Color.LightGray),
                contentAlignment = Alignment.Center
            ) {
                Image(
                    painter = painterResource(R.drawable.ic_launcher_foreground),
                    contentDescription = "Map"
                )
            }

            Spacer(Modifier.height(16.dp))

            OutlinedTextField(
                value = title,
                onValueChange = { title = it },
                label = { Text("Title") },
                placeholder = { Text("Title of address") },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp)
            )

            Spacer(Modifier.height(16.dp))

            OutlinedTextField(
                value = completeAddress,
                onValueChange = { completeAddress = it },
                label = { Text("Complete address *") },
                placeholder = { Text("Enter address") },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(100.dp),
                shape = RoundedCornerShape(12.dp),
                maxLines = 4
            )

            Spacer(Modifier.height(12.dp))

            OutlinedTextField(
                value = floor,
                onValueChange = { floor = it },
                label = { Text("Floor") },
                placeholder = { Text("Enter Floor") },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp)
            )

            Spacer(Modifier.height(12.dp))
            OutlinedTextField(
                value = landmark,
                onValueChange = { landmark = it },
                label = { Text("Landmark") },
                placeholder = { Text("Enter Landmark") },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp)
            )

            Spacer(Modifier.weight(1f))

            CustomButton(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                text = "Save address",
                backgroundColor = Teal,
                textColor = White,
                height = 50,
                cornerRadius = 12,
                onClick = {
                    Log.i("TAG", "AddAddressScreen: ")
                    saveAddress(
                        context = localContext,
                    )
                }
            )

            Spacer(Modifier.height(16.dp))
        }
    }
}

fun saveAddress(context: Context) {

    val graphClient = GraphClient.build(
        context = context,
        shopDomain = "mad45-alex-and02.myshopify.com",
        accessToken = "cf0390c1a174351fc5092b6f62d71a32")

    val customerToken = "3488af256e67f99519ac442366714cf6"

    val addressInput = Storefront.MailingAddressInput().apply {
        address1 = "123 Main St"
        city = "New York"
        country = "United States"
        firstName = "John"
        lastName = "Doe"
        zip = "10001"
        province = "NY"
    }

    val mutation = Storefront.mutation { root ->
        root.customerAddressCreate(customerToken, addressInput) { payload ->
            payload.customerAddress { addr ->
                addr.address1()
                addr.address1()
                addr.city()
            }
            payload.userErrors { err ->
                err.field()
                err.message()
            }
        }
    }

    graphClient.mutateGraph(mutation).enqueue(object : GraphCall.Callback<Storefront.Mutation> {
        override fun onResponse(response: GraphResponse<Storefront.Mutation>) {
            val result = response.data?.customerAddressCreate
            val addr = result?.customerAddress
            val errs = result?.userErrors.orEmpty()

            if (addr != null) {
                Log.d("SAVE_ADDR", "✅ Address created: ${addr.id}")
            } else {
                errs.forEach { Log.e("SAVE_ADDR", "❌ ${it.field}: ${it.message}") }
            }
        }

        override fun onFailure(error: GraphError) {
            Log.e("SAVE_ADDR", "❌ GraphError: ${error.message}", error)
        }
    })
}


//fun saveAddressremote(context: Context) {
//    val token = "3488af256e67f99519ac442366714cf6"
//
//    val addressInput = Storefront.MailingAddressInput().apply {
//        address1 = "123 Main St"
//        city = "New York"
//        country = "United States"
//        firstName = "John"
//        lastName = "Doe"
//        zip = "10001"
//        province = "NY"
//    }
//
////    val query = Storefront.query { root ->
////        root.customer(token) { customer ->
////            customer.id()
////        }
////    }
//    val query = Storefront.query { root ->
//        root.customer(token) { customer ->
//            customer
//        }
//    }
//
//
//    val call =  GraphClient.build(
//        context = context,
//        shopDomain = "mad45-alex-and02.myshopify.com",
//        accessToken = "cf0390c1a174351fc5092b6f62d71a32"
//    ).queryGraph(query)
//
//
//
//    call.enqueue { result ->
//        Log.d("TAG", "updateCustomerDefaultAddress: result")
//        when (result) {
//            is GraphCallResult.Success<*> -> {
//                val defaultAdd = result.response.data
//                Log.i(
//                    "TAG",
//                    "lllllllllll lupdateCustomerDefaultAddress: id: $defaultAdd"
//                )
//            }
//
//            is GraphCallResult.Failure -> {
//                Log.e("TAG", "what yyou want: ${result.error.message}")
//            }
//
//            else -> {}
//        }
//    }
//}





