package com.example.m_commerce.features.cart.data.remote

import android.util.Log
import com.example.m_commerce.features.cart.domain.entity.Cart
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.firestore
import com.shopify.buy3.GraphCallResult
import com.shopify.buy3.GraphClient
import com.shopify.buy3.Storefront
import com.shopify.graphql.support.ID
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class CartRemoteDataSourceImpl @Inject constructor(
    private val clientGraph: GraphClient
) : CartRemoteDataSource  {

    override suspend fun getCartById(id: String): Flow<Cart> = callbackFlow {

        val userDocument = Firebase.firestore.collection("users").document(FirebaseAuth.getInstance().currentUser!!.uid).get().await()
        val cartId  = userDocument.getString("cartId")

        val query = Storefront.query { root ->
            root.cart(ID(cartId)) {
                cart ->
                cart.createdAt()
                cart.updatedAt()
                cart.lines({ it.first(20) }) { lines ->
                    lines.edges { edge ->
                        edge.node { node ->
                            node.id()
                            node.quantity()
                            node.attributes { attr ->
                                attr.key()
                                attr.value()
                            }
                            node.merchandise { merch ->
                                merch.onProductVariant { variant ->
                                    //variant.id()
                                    variant.title()
                                    variant.price { price ->
                                        price.amount()
                                        price.currencyCode()
                                    }
                                    variant.image { image ->
                                        image.url()
                                        image.altText()
                                    }
                                    variant.product { product ->
                                        product.title()
                                    }
                                }
                            }
                        }
                    }
                }
                cart.attributes { attr ->
                    attr.key()
                    attr.value()
                }
                cart.cost { cost ->
                    cost.totalAmount { amt ->
                        amt.amount()
                        amt.currencyCode()
                    }
                    cost.subtotalAmount { amt ->
                        amt.amount()
                        amt.currencyCode()
                    }
                    cost.totalTaxAmount { amt ->
                        amt.amount()
                        amt.currencyCode()
                    }
                    cost.totalDutyAmount { amt ->
                        amt.amount()
                        amt.currencyCode()
                    }
                }
                cart.buyerIdentity { buyer ->
                    buyer.email()
                    buyer.phone()
                    buyer.countryCode()
                    buyer.customer { customer ->
                        customer.id()
                    }
                }
            }
        }

        clientGraph.queryGraph(query).enqueue { result ->
            when (result) {
                is GraphCallResult.Success -> {
                    val data = result.response.data?.cart
                    if (data != null) {
                        val cart = Cart.fromGraphQL(data)
                        trySend(cart)
                    } else {
                        close(Throwable("Cart not found"))
                    }
                }
                is GraphCallResult.Failure -> close(result.error)
            }
        }
        awaitClose {}
    }.flowOn(Dispatchers.IO)


    override suspend fun updateCart(cartLineId: String, quantity: Int): Flow<Boolean> = callbackFlow {
        try {
            val userDocument = Firebase.firestore.collection("users").document(FirebaseAuth.getInstance().currentUser!!.uid).get().await()
            val cartId  = userDocument.getString("cartId")

            val lineInput = Storefront.CartLineUpdateInput(ID(cartLineId))
            lineInput.setQuantity(quantity)

            val mutation = Storefront.mutation { root ->
                root.cartLinesUpdate(ID(cartId), listOf(lineInput)) { payload ->
                    payload.cart { }
                    payload.userErrors { err ->
                        err.field()
                        err.message()
                    }
                }
            }

            clientGraph.mutateGraph(mutation).enqueue { result ->
                when (result) {
                    is GraphCallResult.Success -> {
                        val userErrors = result.response.data?.cartLinesUpdate?.userErrors
                        if (userErrors.isNullOrEmpty()) {
                            Log.i("TAG", "updateCart success")
                            trySend(true)
                        } else {
                            val message = userErrors.joinToString { it.message }
                            Log.e("TAG", " Shopify user error: $message")
                            close(Throwable(message))
                        }
                    }
                    is GraphCallResult.Failure -> {
                        Log.e("TAG", "call failed: ${result.error.message}")
                        close(result.error)
                    }
                }
            }
        } catch (e: Exception) {
            Log.e("TAG", "field", e)
            close(e)
        }
        awaitClose {}
    }.flowOn(Dispatchers.IO)


    override suspend fun removeProductVariant(cartLineId: String): Flow<Boolean> = callbackFlow {
        try {
            val userDocument = Firebase.firestore.collection("users").document(FirebaseAuth.getInstance().currentUser!!.uid).get().await()
            val cartId  = userDocument.getString("cartId")

            val mutation = Storefront.mutation { root ->
                root.cartLinesRemove(ID(cartId), listOf(ID(cartLineId))) { payload ->
                    payload.cart { }
                    payload.userErrors { err ->
                        err.field()
                        err.message()
                    }
                }
            }

            clientGraph.mutateGraph(mutation).enqueue { result ->
                when (result) {
                    is GraphCallResult.Success -> {
                        val userErrors = result.response.data?.cartLinesRemove?.userErrors
                        if (userErrors.isNullOrEmpty()) {
                            trySend(true)
                        } else {
                            val message = userErrors.joinToString { it.message }
                            Log.e("TAG", "user error: $message")
                            close(Throwable(message))
                        }
                    }

                    is GraphCallResult.Failure -> {
                        Log.e("TAG", " ${result.error.message}")
                        close(result.error)
                    }
                }
            }

        } catch (e: Exception) {
            Log.e("Cart", "failed", e)
            close(e)
        }

        awaitClose {}
    }.flowOn(Dispatchers.IO)

}