package com.example.m_commerce.features.orders.data.remote

import com.example.m_commerce.features.orders.data.model.DraftOrderCreateVariables
import com.example.m_commerce.features.orders.data.model.GraphQLRequest
import com.example.m_commerce.features.orders.data.model.OrderCreateResponse
import com.example.m_commerce.features.orders.domain.entity.CreatedOrder
import kotlinx.coroutines.flow.Flow
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.POST


interface ShopifyAdminApiService {
    @POST("graphql.json")
    @Headers("Content-Type: application/json")
    suspend fun createOrder(
        @Body body: GraphQLRequest<DraftOrderCreateVariables>,
        @Header("X-Shopify-Access-Token") token: String
    ): Response<OrderCreateResponse>
}