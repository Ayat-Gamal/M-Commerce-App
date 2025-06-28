package com.example.m_commerce.features.product.data.remote

import android.util.Log
import com.example.m_commerce.features.product.data.mapper.toDomain
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.shopify.buy3.GraphCallResult
import com.shopify.buy3.GraphClient
import com.shopify.buy3.Storefront
import com.shopify.buy3.Storefront.CartLineInput
import com.shopify.graphql.support.ID
import com.shopify.graphql.support.Input
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException

class ProductRemoteDataSourceImpl @Inject constructor(
    private val graphClient: GraphClient,
    private val db: FirebaseFirestore
) :
    ProductRemoteDataSource {
    override fun getProductById(productId: String) = flow {
        val product = suspendCancellableCoroutine { cont ->
            val query = Storefront.query { rootQuery ->
                rootQuery.product({ args ->
                    args.id(ID(productId))
                }) { product ->
                    product
                        .title()
                        .description()
                        .productType()
                        .vendor()
                        .availableForSale()
                        .variants({ args -> args.first(10) }) { variants ->
                            variants.edges { edges ->
                                edges.node { node ->
                                    node.price { price ->
                                        price.amount().currencyCode()
                                    }
                                        .title()
                                        .selectedOptions { it.name().value() }
                                }
                            }
                        }
                        .images({ args -> args.first(10) }) { images -> images.edges { edges -> edges.node { it.url() } } }
                }
            }

            graphClient.queryGraph(query).enqueue { result ->
                when (result) {
                    is GraphCallResult.Success -> {
                        val graphQlProduct = result.response.data?.product

                        if (graphQlProduct != null) {
                            cont.resume(graphQlProduct.toDomain())
                        } else {
                            cont.resumeWithException(Exception("Product not found"))
                        }
                    }

                    is GraphCallResult.Failure -> {
                        cont.resumeWithException(result.error)
                    }
                }
            }
        }
        emit(product)
    }

    override fun addProductVariantToCart(productVariantId: String, quantity: Int) = flow {
        val user = FirebaseAuth.getInstance().currentUser
        if (user == null) {
            emit(false)
            return@flow
        }

        val userDocument = db.collection("users").document(user.uid).get().await()
        val cartIdString = userDocument.getString("cartId")
        if (cartIdString.isNullOrEmpty()) {
            emit(false)
            return@flow
        }

        val cartId = ID(cartIdString)
        val merchandiseId = ID(productVariantId)

        val isAdded = suspendCancellableCoroutine { cont ->
            val mutation = Storefront.mutation { mutationBuilder ->
                mutationBuilder.cartLinesAdd(
                    cartId,
                    listOf(CartLineInput(merchandiseId).setQuantityInput(Input.value(quantity)))
                ) { cartLinesAdd ->
                    cartLinesAdd.cart {}
                }
            }

            graphClient.mutateGraph(mutation).enqueue { result ->
                when (result) {
                    is GraphCallResult.Success -> {
                        if (result.response.hasErrors) {
                            cont.resumeWithException(Exception(result.response.errors.joinToString { it.message() }))
                        } else {
                            Log.i("TAG", "addProductVariantToCart: Success")
                            cont.resume(true)
                        }
                    }

                    is GraphCallResult.Failure -> {
                        cont.resumeWithException(result.error)
                    }
                }
            }

        }
        emit(isAdded)
    }
}
