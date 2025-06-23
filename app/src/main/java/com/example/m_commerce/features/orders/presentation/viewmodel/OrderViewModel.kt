package com.example.m_commerce.features.orders.presentation.viewmodel

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.m_commerce.BuildConfig
import com.example.m_commerce.features.orders.data.model.GraphQLRequestWithVariables
import com.example.m_commerce.features.orders.data.model.completeDraftOrderMutation
import com.example.m_commerce.features.orders.data.model.mutationQuery
import com.example.m_commerce.features.orders.data.model.shopifyApi
import com.example.m_commerce.features.orders.data.model.variablesCompleteOrder
import com.example.m_commerce.features.orders.data.model.variablesCreateOrder
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@RequiresApi(Build.VERSION_CODES.O)
@HiltViewModel
class OrderViewModel @Inject constructor() : ViewModel() {

    val token = BuildConfig.ADMIN_TOKEN
    val completeRequest = GraphQLRequestWithVariables(
        query = completeDraftOrderMutation,
        variables = variablesCompleteOrder
    )

    val createRequest = GraphQLRequestWithVariables(
        query = mutationQuery,
        variables = variablesCreateOrder
    )


    fun completeOrder() = viewModelScope.launch {
        try {
            val response = shopifyApi.createOrder(
                completeRequest, token
            )

            if (response.isSuccessful) {
                val body = response.body()?.string()
                Log.d("Shopify", "Draft order created: $body")
            } else {
                Log.e("Shopify", "Error ${response.code()}: ${response.errorBody()?.string()}")
            }
        } catch (e: Exception) {
            Log.e("Shopify", "Exception: ${e.message}")
        }
    }

    fun createOrder() = viewModelScope.launch {
            try {
                val response = shopifyApi.createOrder(
                    body = createRequest,
                    token = token
                )

                if (response.isSuccessful) {

                    val responseBody = response.body()?.string()
                    Log.d("Shopify", "Order created: $responseBody")
                } else {
                    Log.e("Shopify", "Error ${response.code()}: ${response.errorBody()?.string()}")
                }

            } catch (e: Exception) {
                Log.e("Shopify", "Request failed: ${e.message}")
            }
        }


}