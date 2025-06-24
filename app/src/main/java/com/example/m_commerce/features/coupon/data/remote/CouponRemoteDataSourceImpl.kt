package com.example.m_commerce.features.coupon.data.remote


import android.util.Log
import com.example.m_commerce.features.coupon.domain.entity.Coupon
import com.example.m_commerce.features.orders.data.remote.ShopifyAdminApiService
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
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class CouponRemoteDataSourceImpl @Inject constructor(
    private val clientGraph: GraphClient , private val service: ShopifyAdminApiService
) : CouponRemoteDataSource {

    override suspend fun getCoupons(token: String): Flow<List<Coupon>> = flow {
        val allCoupons = mutableListOf<Coupon>()

        val priceRulesResponse = service.getPriceRules(token)
        if (!priceRulesResponse.isSuccessful) {
            throw Exception("Failed to fetch price rules: ${priceRulesResponse.errorBody()?.string()}")
        }

        val priceRules = priceRulesResponse.body()?.priceRules

        if (priceRules != null) {
            for (rule in priceRules) {
                val codesResponse = service.getDiscountCodes(rule.id.toString(), token)
                if (!codesResponse.isSuccessful) continue

                val codes = codesResponse.body()?.discountCodes ?: continue

                allCoupons.addAll(
                    codes.map {
                        Coupon(
                            id = it.id,
                            code = it.code,
                            usageCount = it.usage_count,
                            status = it.status ?: ""
                        )
                    }
                )
            }
        }

        emit(allCoupons)
    }.flowOn(Dispatchers.IO)


    override suspend fun applyCoupon(discountCode: String): Flow<Boolean> = callbackFlow {
        val cartId = getCurrentCartId()
        if (cartId == null) {
            close(Throwable("Cart ID not found"))
            return@callbackFlow
        }

        val mutation = Storefront.mutation { root ->
            root.cartDiscountCodesUpdate(
                ID(cartId),
                { arg -> arg.discountCodes(listOf(discountCode)) }
            ) { payload ->
                payload.cart { cart ->
                    cart.discountCodes { code ->
                        code.code()
                        code.applicable()
                    }
                }
                payload.userErrors { err ->
                    err.field()
                    err.message()
                }
            }
        }


        clientGraph.mutateGraph(mutation).enqueue { result ->
            when (result) {
                is GraphCallResult.Success -> {
                    val userErrors = result.response.data?.cartDiscountCodesUpdate?.userErrors
                    if (userErrors.isNullOrEmpty()) {
                        trySend(true)
                    } else {
                        val message = userErrors.joinToString { it.message }
                        Log.e("CouponRemote", "applyCoupon user error: $message")
                        close(Throwable(message))
                    }
                }

                is GraphCallResult.Failure -> {
                    Log.e("CouponRemote", "applyCoupon failed", result.error)
                    close(result.error)
                }
            }
        }
        awaitClose {}
    }.flowOn(Dispatchers.IO)

    private suspend fun getCurrentCartId(): String? {
        return try {
            val user = FirebaseAuth.getInstance().currentUser
            if (user != null) {
                val document = Firebase.firestore
                    .collection("users")
                    .document(user.uid)
                    .get()
                    .await()
                document.getString("cartId")
            } else {
                null
            }
        } catch (e: Exception) {
            Log.e("CouponRemote", "Failed to get cart ID", e)
            null
        }
    }
}
