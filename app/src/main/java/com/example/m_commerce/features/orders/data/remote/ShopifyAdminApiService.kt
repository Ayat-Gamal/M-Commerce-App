package com.example.m_commerce.features.orders.data.remote

import com.example.m_commerce.features.coupon.domain.entity.DiscountCodesResponse
import com.example.m_commerce.features.coupon.domain.entity.PriceRulesResponse
import com.example.m_commerce.features.orders.data.model.CompleteOrderWrapper
import com.example.m_commerce.features.orders.data.model.GraphQLRequest
import com.example.m_commerce.features.orders.data.model.OrderCreateResponse
import com.example.m_commerce.features.orders.data.model.variables.CompleteOrderVariables
import com.example.m_commerce.features.orders.data.model.variables.DraftOrderCreateVariables
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.Path


interface ShopifyAdminApiService {
    @POST("graphql.json")
    @Headers("Content-Type: application/json")
    suspend fun createOrder(
        @Body body: GraphQLRequest<DraftOrderCreateVariables>,
        @Header("X-Shopify-Access-Token") token: String
    ): Response<OrderCreateResponse>

    @POST("graphql.json")
    @Headers("Content-Type: application/json")
    suspend fun completeOrder(
        @Body body: GraphQLRequest<CompleteOrderVariables>,
        @Header("X-Shopify-Access-Token") token: String
    ): Response<CompleteOrderWrapper>

    @GET("price_rules/{price_rule_id}/discount_codes.json")
    suspend fun getDiscountCodes(
        @Path("price_rule_id") priceRuleId: String,
        @Header("X-Shopify-Access-Token") token: String
    ): Response<DiscountCodesResponse>

    @GET("price_rules.json")
    suspend fun getPriceRules(
        @Header("X-Shopify-Access-Token") token: String
    ): Response<PriceRulesResponse>

}