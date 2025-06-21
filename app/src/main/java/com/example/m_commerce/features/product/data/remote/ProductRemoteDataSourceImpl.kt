package com.example.m_commerce.features.product.data.remote

import android.util.Log
import com.example.m_commerce.features.product.data.mapper.toDomain
import com.shopify.buy3.GraphCallResult
import com.shopify.buy3.GraphClient
import com.shopify.buy3.Storefront
import com.shopify.graphql.support.ID
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.suspendCancellableCoroutine
import javax.inject.Inject
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException

class ProductRemoteDataSourceImpl @Inject constructor(private val graphClient: GraphClient) :
    ProductRemoteDataSource {
    override fun getProductById(productId: String) = flow {
        val product = suspendCancellableCoroutine { cont ->
            val query = Storefront.query { rootQuery ->
                rootQuery.product({ args ->
                    args.id(ID(productId)) // TODO
                }) { product ->
                    product
                        .title()
                        .description()
                        .productType()
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
}