package com.example.m_commerce.features.orders.data.remote

import android.util.Log
import com.example.m_commerce.features.orders.data.model.GraphQLRequest
import com.example.m_commerce.features.orders.data.model.toDomain
import com.example.m_commerce.features.orders.data.model.variables.CompleteOrderVariables
import com.example.m_commerce.features.orders.data.model.variables.DraftOrderCreateVariables
import com.example.m_commerce.features.orders.data.model.variables.LineItem
import com.example.m_commerce.features.orders.domain.entity.CompletedOrder
import com.example.m_commerce.features.orders.domain.entity.CreatedOrder
import com.example.m_commerce.features.orders.domain.entity.OrderHistory
import com.example.m_commerce.features.orders.presentation.ui_state.OrderHistoryUiState
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.shopify.buy3.GraphCallResult
import com.shopify.buy3.GraphClient
import com.shopify.buy3.Storefront
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class OrderDataSourceImpl @Inject constructor(
    private val service: ShopifyAdminApiService,
    private val graphClient: GraphClient,
    private val firestore: FirebaseFirestore,
    private val auth: FirebaseAuth

) : OrderDataSource {
    override fun createOrder(
        body: GraphQLRequest<DraftOrderCreateVariables>,
        token: String
    ): Flow<CreatedOrder> = flow {
        val response = service.createOrder(body, token)
        if (response.isSuccessful) {
            val order = response.body()?.data?.draftOrderCreate?.draftOrder
            if (order != null) {
                emit(order.toDomain())
            } else {
                throw Exception("Draft order creation failed: Empty response")
            }
        } else {
            val errorBody = response.errorBody()?.string()
            throw Exception("HTTP ${response.code()}: $errorBody")
        }
    }.flowOn(Dispatchers.IO)


    override fun completeOrder(
        body: GraphQLRequest<CompleteOrderVariables>,
        token: String
    ): Flow<CompletedOrder> = flow {
        val response = service.completeOrder(body, token)
        if (response.isSuccessful) {
            val order = response.body()?.data?.draftOrderComplete?.draftOrder
            if (order != null) {
                emit(order.toDomain())
            } else {
                throw Exception("Complete order failed: Empty response")
            }
        } else {
            val errorBody = response.errorBody()?.string()
            throw Exception("HTTP ${response.code()}: $errorBody")
        }
    }.flowOn(Dispatchers.IO)


    override fun fetchOrders(): Flow<OrderHistoryUiState> = callbackFlow {

        val userId = auth.currentUser?.uid

        val userDocument = firestore.collection("users").document(userId ?: "").get()
        val userToken = userDocument.await().getString("shopifyToken")

        val query = Storefront.query { root ->
            root.customer(userToken) { customer ->
                customer.orders({ it.first(50) }) { orders ->
                    orders.nodes { order ->
                        order
                            .name()
                            .currencyCode()
                            .processedAt()
                            .financialStatus()
                            .shippingAddress { address ->
                                address.address1()
                                address.city()
                                address.zip()
                            }
                            .totalPrice { it.amount() }
                            .lineItems({ it.first(10) }) {
                                it.nodes { item ->
                                    item.title()
                                    item.quantity()
                                    item.variant {
                                        it.title()
                                        it.image {
                                            it.url()

                                        }

                                    }
                                }
                            }
                    }
                }
            }
        }

        Log.d("OrderHistory", "Executing graphClient.queryGraph...")
        Log.d("OrderHistory", "fetchOrders STARTED for token: $userToken")


        graphClient.queryGraph(query).enqueue { result ->
            when (result) {
                is GraphCallResult.Success -> {
                    val orders = result.response.data?.customer?.orders?.nodes?.mapNotNull { order ->
                        Log.d("OrderHistory", "fetchOrders: ${order.processedAt}")

                        OrderHistory(
                            id = order.name.orEmpty(),
                            shippedTo = listOfNotNull(
                                order.shippingAddress?.address1,
                                order.shippingAddress?.city,
                                order.shippingAddress?.zip
                            ).joinToString(", "),
                            createdAt = order.processedAt.toString(),
                            status = order.financialStatus.toString(),
                            currencyCode = (order.currencyCode ?: "").toString(),
                            totalPrice = order.totalPrice.amount.toString(),
                            items = order.lineItems.nodes.map {
                                LineItem(
                                    variantId = it.title.orEmpty(),
                                    quantity = it.quantity,
                                    originalUnitPrice = "",
                                    image = it.variant?.image?.url ?: "",
                                    title = it.title,
                                    specs = it.variant?.title ?: "",

                                )
                            },

                        )
                    }.orEmpty()

                    trySend(OrderHistoryUiState.Success(orders))
                }

                is GraphCallResult.Failure -> {
                    trySend(OrderHistoryUiState.Error(result.error.message ?: "Failed to fetch orders"))
                    Log.d("OrderHistory", "fetchOrders: ${result.error.message}")
                }
            }
            close()
        }
        awaitClose()
    }.flowOn(Dispatchers.IO)
}