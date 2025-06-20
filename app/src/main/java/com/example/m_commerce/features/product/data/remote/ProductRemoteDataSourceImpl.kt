package com.example.m_commerce.features.product.data.remote

import android.content.Context
import android.util.Log
import com.shopify.buy3.GraphCallResult
import com.shopify.buy3.GraphClient
import com.shopify.buy3.Storefront
import com.shopify.graphql.support.ID
import kotlinx.coroutines.flow.flow
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

class ProductRemoteDataSourceImpl(val context: Context) : ProductRemoteDataSource {
    suspend fun getProductById() = flow {

        val graphClient =  GraphClient.build(
            context = context,
            shopDomain = "mad45-alex-and02.myshopify.com",
            accessToken = "cf0390c1a174351fc5092b6f62d71a32"
        )
        val product = suspendCoroutine { cont ->
            Log.i("TAG", "getProductById: called")
            val query = Storefront.query { rootQuery ->
                rootQuery.product({ args ->
                    args.id(ID("gid://shopify/Product/8845369016569"))
                }) { product ->
                    product
                        .title()
                        .vendor()
                }
            }

            graphClient.queryGraph(query).enqueue { result ->
                when (result) {
                    is GraphCallResult.Success -> {
                        val title = result.response.data
                            ?.product
                            ?.vendor

                        if (title != null) {
                            Log.i("TAG", "product title: $title")
                            cont.resume(title)
                        } else {
                            Log.e("TAG", "title is null")
                            cont.resume(Unit)
                        }
                    }

                    is GraphCallResult.Failure -> {
                        Log.e("TAG", "Failure")
                        cont.resumeWith(Result.failure(result.error))
                    }
                }
            }
        }
        emit(Unit)
    }
}