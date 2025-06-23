package com.example.m_commerce.features.orders.data.model

import com.example.m_commerce.features.orders.domain.entity.CreatedOrder
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.POST

data class OrderResponse(
    val id: String,
    val name: String,
    val createdAt: String,
    val totalPriceSet: TotalPriceSet
)

data class TotalPriceSet(
    val shopMoney: ShopMoney
)

data class ShopMoney(
    val amount: String,
    val currencyCode: String
)

fun OrderResponse.toDomain(): CreatedOrder {
    return CreatedOrder(
        id = this.id,
        number = this.name,
        totalAmount = this.totalPriceSet.shopMoney.amount,
        currency = this.totalPriceSet.shopMoney.currencyCode,
        createdAt = this.createdAt
    )
}


data class GraphQLRequestWithVariables(
    val query: String,
    val variables: Map<String, Any?>
)

//val shippingAddress = "\$shippingAddress"
//val lineItems = "\$lineItems"
//val note = "\$note"
//val email = "\$email"

val mutationQuery = """
mutation CreateDraftOrder(
    ${"$"}email: String!,
    ${"$"}shippingAddress: MailingAddressInput!,
    ${"$"}lineItems: [DraftOrderLineItemInput!]!,
    ${"$"}note: String
) {
  draftOrderCreate(input: {
    email: ${"$"}email,
    shippingAddress: ${"$"}shippingAddress,
    lineItems: ${"$"}lineItems,
    note: ${"$"}note
  }) {
    draftOrder {
      id
      name
      createdAt
      invoiceUrl
    }
    userErrors {
      field
      message
    }
  }
}
""".trimIndent()


val completeDraftOrderMutation = """
mutation CompleteDraftOrder(${"$"}id: ID!) {
  draftOrderComplete(id: ${"$"}id, paymentPending: false) {
    draftOrder {
      id
      name
      status
    }
    userErrors {
      field
      message
    }
  }
}
""".trimIndent()

val variablesCompleteOrder = mapOf(
    "id" to "gid://shopify/DraftOrder/1254777389305"
)


val variablesCreateOrder = mapOf(
    "email" to "youssifn.mostafa@gmail.com",
    "shippingAddress" to mapOf(
        "firstName" to "Youssif",
        "lastName" to "Nasser",
        "address1" to "123 Main Street",
        "city" to "Cairo",
        "country" to "Egypt",
        "zip" to "12345"
    ),
    "lineItems" to listOf(
        mapOf(
            "variantId" to "gid://shopify/ProductVariant/46559952797945",
            "quantity" to 2,
            "originalUnitPrice" to "150.00"
        )
    ),
    "note" to null
)

interface ShopifyAdminApiService {
    @POST("graphql.json")
    @Headers("Content-Type: application/json")
    suspend fun createOrder(
        @Body body: GraphQLRequestWithVariables,
        @Header("X-Shopify-Access-Token") token: String
    ): Response<ResponseBody>
}


val retrofit = Retrofit.Builder()
    .baseUrl("https://mad45-alex-and02.myshopify.com/admin/api/2024-04/")
    .addConverterFactory(GsonConverterFactory.create())
    .build()

val shopifyApi = retrofit.create(ShopifyAdminApiService::class.java)

