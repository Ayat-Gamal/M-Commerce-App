package com.example.m_commerce.features.product.data.remote

import com.example.m_commerce.features.product.data.mapper.toDomain
import com.example.m_commerce.features.product.presentation.ProductUiState
import com.shopify.buy3.GraphCallResult
import com.shopify.buy3.GraphClient
import com.shopify.buy3.Storefront
import com.shopify.graphql.support.ID
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.suspendCancellableCoroutine
import javax.inject.Inject
import kotlin.coroutines.resume

class ProductRemoteDataSourceImpl @Inject constructor(private val graphClient: GraphClient) :
    ProductRemoteDataSource {
    override fun getProductById(productId: String) = flow {
        val uiState = suspendCancellableCoroutine { cont ->
            val query = Storefront.query { rootQuery ->
                rootQuery.product({ args ->
                    args.id(ID("gid://shopify/Product/8845369016569"))
                }) { product ->
                    product.title().description().productType()
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
//                        .vendor()
//                        .images {
//                            it.edges {}
//                        }
                }
            }

            graphClient.queryGraph(query).enqueue { result ->
                when (result) {
                    is GraphCallResult.Success -> {
                        val graphQlProduct = result.response.data?.product

                        if (graphQlProduct != null) {
                            val product = graphQlProduct.toDomain()
                            cont.resume(ProductUiState.Success(product))
                        } else {
                            cont.resume(ProductUiState.Error("Couldn't fetch Product"))
                        }
                    }

                    is GraphCallResult.Failure -> {
                        cont.resume(
                            ProductUiState.Error(
                                result.error.message ?: "Couldn't fetch Product"
                            )
                        )
                    }
                }
            }
        }
        emit(uiState)
    }
}