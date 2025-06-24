package com.example.m_commerce.features.search.data.remote

import com.example.m_commerce.features.product.data.mapper.toDomain
import com.example.m_commerce.features.product.domain.entities.Product
import com.shopify.buy3.GraphCallResult
import com.shopify.buy3.GraphClient
import com.shopify.buy3.Storefront
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.suspendCancellableCoroutine
import javax.inject.Inject
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException

class ProductsRemoteDataSourceImpl @Inject constructor(
    private val graphClient: GraphClient,
) : ProductsRemoteDataSource {
    override fun getProducts(): Flow<List<Product>> = flow {
        val result = suspendCancellableCoroutine { cont ->
            val query = Storefront.query { rootQuery ->
                rootQuery.products({ it.first(50) }) { products ->
                    products.edges { edges ->
                        edges.node { node ->
                            node.title()
                                .description()
                                .productType()
                                .vendor()
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
                }
            }

            graphClient.queryGraph(query).enqueue { result ->
                when (result) {
                    is GraphCallResult.Success -> {
                        val graphQlProducts = result.response.data?.products?.edges


                        if (graphQlProducts != null) {
                            val products = graphQlProducts.map { it.toDomain() }
                                .toList()
                            cont.resume(products)
                        } else {
                            cont.resumeWithException(Exception("Products not found"))
                        }
                    }

                    is GraphCallResult.Failure -> {
                        cont.resumeWithException(result.error)
                    }
                }
            }
        }
        emit(result)
    }
}