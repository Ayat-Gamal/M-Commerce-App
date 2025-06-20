package com.example.m_commerce.features.brand.data.datasources.remote

import com.example.m_commerce.features.brand.data.dto.BrandDto
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
    override fun getBrands(): Flow<List<BrandDto>> = callbackFlow {
        val query = Storefront.query { root ->
            root.collections({ it.first(20) }) { brand ->
                brand.nodes {
                    it.title()
                    it.image { it.url() }
                }
            }
        }
//        1200
        shopifyClient.queryGraph(query).enqueue { result ->
            when (result) {
                is GraphCallResult.Success -> {
                    val brands =
                        result.response.data?.collections?.nodes?.mapNotNull {
//                            Log.d("QL", "${it.title} -- ${it.id}")
                            BrandDto(
                                id = it.id.toString(),
                                image = it.image?.url,
                                name = it.title
                            )
                        } ?: emptyList()
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
}