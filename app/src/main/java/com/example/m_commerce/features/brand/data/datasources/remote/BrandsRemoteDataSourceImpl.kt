package com.example.m_commerce.features.brand.data.datasources.remote

import com.example.m_commerce.core.utils.extentions.toDomain
import com.example.m_commerce.features.brand.data.dto.BrandDto
import com.example.m_commerce.features.product.data.mapper.toDomain
import com.example.m_commerce.features.product.domain.entities.Product
import com.shopify.buy3.GraphCallResult
import com.shopify.buy3.GraphClient
import com.shopify.buy3.Storefront
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class BrandsRemoteDataSourceImpl @Inject constructor(private val shopifyClient: GraphClient) : BrandsRemoteDataSource {
    override fun getBrands(first: Int): Flow<List<BrandDto>> = callbackFlow {
        val query = Storefront.query { root ->
            root.collections({ it.first(first) }) { brand ->
                brand.nodes {
                    it.title()
                    it.image { it.url() }
                }
            }
        }

        shopifyClient.queryGraph(query).enqueue { result ->
            when (result) {
                is GraphCallResult.Success -> {

                    val brands = result.response.data?.collections?.toDomain() ?: emptyList()

                    trySend(brands)
                    close()
                }

                is GraphCallResult.Failure -> {
                    close(result.error)
                }
            }
        }
        awaitClose()

    }.flowOn(Dispatchers.IO)


    override fun getProductsByBrandName(brandName: String): Flow<List<Product>> = callbackFlow {

        val query = Storefront.query { rootQuery ->
            rootQuery.products({ it.first(250).query(brandName) }) {
                it.nodes { product ->
                    product.title()
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

        shopifyClient.queryGraph(query).enqueue { result ->
            when (result) {
                is GraphCallResult.Success -> {
                    val products = result.response.data?.products?.nodes?.map { it.toDomain() } ?: emptyList()
                    trySend(products)
                    close()
                }

                is GraphCallResult.Failure -> {
                    close(result.error)

                }
            }
        }
        awaitClose()

    }.flowOn(Dispatchers.IO)
}

