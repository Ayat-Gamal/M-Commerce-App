package com.example.m_commerce.features.auth.data.remote

import android.util.Log
import com.shopify.buy3.GraphCallResult
import com.shopify.buy3.GraphClient
import com.shopify.buy3.Storefront
import kotlinx.coroutines.flow.flow
import javax.inject.Inject
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

class CustomersRemoteDataSourceImpl @Inject constructor(
    private val graphClient: GraphClient
) : CustomersRemoteDataSource {
    override suspend fun createCustomer(
        email: String,
        name: String,
        password: String
    ) = suspendCoroutine{ cont ->
        val input = Storefront.CustomerCreateInput(email, password)
            .setFirstName(name)

        val mutation = Storefront.mutation { mutationQuery ->
            mutationQuery.customerCreate(input) { customerCreate ->
                customerCreate.customer { customer ->
                    customer.email().displayName().id()
                }.customerUserErrors { it.message() }
            }
        }
        graphClient.mutateGraph(mutation).enqueue { result ->
            when (result) {
                is GraphCallResult.Success -> {
                    val customer = result.response.data?.customerCreate?.customer
                    Log.i(
                        "TAG",
                        "createCustomer: email: ${customer?.email}, name: ${customer?.displayName},id: ${customer?.id}"
                    )
                }

                is GraphCallResult.Failure -> {
                    Log.i("TAG", "createCustomer: ${result.error.message}")
                }
            }
            cont.resume(Unit)
        }
    }

    override suspend fun createCustomerToken(email: String, password: String, name: String) = flow {
        createCustomer(email, name , password)
        val token = suspendCoroutine { cont ->
            Log.i("TAG", "createCustomerToken: called")
            val input = Storefront.CustomerAccessTokenCreateInput(email, password)
            val mutation = Storefront.mutation { mutationQuery ->
                mutationQuery.customerAccessTokenCreate(input) { customerAccessTokenCreate ->
                    customerAccessTokenCreate.customerAccessToken { customerAccessToken ->
                        customerAccessToken.accessToken().expiresAt()
                    }
                }
            }

            graphClient.mutateGraph(mutation).enqueue { result ->
                when (result) {
                    is GraphCallResult.Success -> {
                        val token = result.response.data
                            ?.customerAccessTokenCreate
                            ?.customerAccessToken
                            ?.accessToken

                        if (token != null) {
                            cont.resume(token)
                            Log.i("TAG", "createCustomerToken: $token")
                        } else {
                            cont.resumeWith(Result.failure(IllegalStateException("Token is null")))
                        }
                    }

                    is GraphCallResult.Failure -> {
                        cont.resumeWith(Result.failure(result.error))
                    }
                }
            }
        }
        emit(token)
    }

    override suspend fun createCustomerCart() = flow {
        val token = "3488af256e67f99519ac442366714cf6"
        val cartID = suspendCoroutine { cont ->
            val buyerIdentity = Storefront.CartBuyerIdentityInput()
                .setCustomerAccessToken(token)

            val input = Storefront.CartInput()
                .setBuyerIdentity(buyerIdentity)

            val mutation = Storefront.mutation { mutationQuery ->
                mutationQuery.cartCreate({ it.input(input) }) { cartCreate ->
                    cartCreate.cart { cart ->
                        //cart.id
                    }
                }
            }

            graphClient.mutateGraph(mutation).enqueue { result ->
                when (result) {
                    is GraphCallResult.Success -> {
                        val cartId = result.response.data


                        if (cartId != null) {
                            cont.resume(token)
                            Log.i("TAG", "createCustomerToken: $cartId")
                        } else {
                            cont.resumeWith(Result.failure(IllegalStateException("Token is null")))
                        }
                    }

                    is GraphCallResult.Failure -> {
                        cont.resumeWith(Result.failure(result.error))
                    }
                }
            }
        }
        emit(cartID)
    }


}