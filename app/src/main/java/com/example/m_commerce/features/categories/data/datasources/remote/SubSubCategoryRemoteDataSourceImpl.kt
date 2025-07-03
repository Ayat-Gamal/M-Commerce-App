package com.example.m_commerce.features.categories.data.datasources.remote

import com.example.m_commerce.features.categories.data.dto.CategoryDto
import com.shopify.buy3.GraphCallResult
import com.shopify.buy3.GraphClient
import com.shopify.buy3.Storefront
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class SubSubCategoryRemoteDataSourceImpl @Inject constructor(private val client: GraphClient) : SubCategoryRemoteDataSource {
    override fun getCategories(): Flow<List<CategoryDto>> = callbackFlow {

        val query = Storefront.query { root ->
            root.productTypes(20) {
                it.nodes()
            }
        }
        client.queryGraph(query).enqueue { result ->
            when (result) {
                is GraphCallResult.Success -> {
                    val categories =
                        result.response.data?.productTypes?.nodes?.mapNotNull { it ->
                            CategoryDto(
                                id = "it.id.toString()",
                                name = it,
                                image = null
                            )

                        } ?: emptyList()
                    trySend(categories)
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


