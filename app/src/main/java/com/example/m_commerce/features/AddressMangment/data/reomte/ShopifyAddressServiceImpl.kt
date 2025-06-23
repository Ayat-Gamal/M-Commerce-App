package com.example.m_commerce.features.AddressMangment.data.reomte

import android.util.Log
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.firestore
import com.shopify.buy3.GraphCallResult
import com.shopify.buy3.GraphClient
import com.shopify.buy3.Storefront
import com.shopify.graphql.support.ID
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class ShopifyAddressServiceImpl @Inject constructor(private val graphClient: GraphClient) :
    ShopifyAddressService {

    override suspend fun saveAddress(address: Storefront.MailingAddressInput) {

        val userDocument = Firebase.firestore.collection("users").document(FirebaseAuth.getInstance().currentUser!!.uid).get().await()
        val customerToken = userDocument.getString("shopifyToken")
        Log.i("Token", "saveAddress: ${customerToken} ")

        val mutation = Storefront.mutation { root ->
            root.customerAddressCreate(customerToken, address) { subQuery ->
                subQuery
                    .customerAddress { addressQuery ->
                        addressQuery.firstName().address2().lastName().address1().city().country().zip()
                            .phone()
                    }
                    .userErrors { errorQuery ->
                        errorQuery.field().message()
                    }
            }
        }

        graphClient.mutateGraph(mutation).enqueue { result ->
            when (result) {
                is GraphCallResult.Success -> {
                    val payload = result.response.data?.customerAddressCreate
                    val address = payload?.customerAddress
                    val errors = payload?.userErrors

                    if (address != null && errors.isNullOrEmpty()) {
                        Log.i("TAG", "Address saved: ${address.address1}  2 ${address.address1} ${address.city} ${address.country}  ")
                    } else {
                        Log.i("Shopify", errors?.joinToString { it.message } ?: "Unknown error")
                    }
                }

                is GraphCallResult.Failure -> {
                    Log.i("Shopify", "error: ")
                }
            }
        }
    }


    suspend fun getCustomerAddresses (onResult: (List<Storefront.MailingAddress>) -> Unit) {

        val userDocument = Firebase.firestore.collection("users").document(FirebaseAuth.getInstance().currentUser!!.uid).get().await()
        val customerToken = userDocument.getString("shopifyToken")
        Log.i("Token", "saveAddress: ${customerToken} ")

        val query = Storefront.query { root ->
            root.customer(customerToken) { customerQuery ->
                customerQuery.addresses({ it.first(150) }) { addressConnectionQuery ->
                    addressConnectionQuery.edges { edgeQuery ->
                        edgeQuery.node { addressNode ->
                            addressNode.firstName().lastName().address1().city().country().zip()
                                .phone()
                        }
                    }
                }
            }
        }

        graphClient.queryGraph(query).enqueue { result ->
            when (result) {
                is GraphCallResult.Success -> {
                    //flag
                    val addresses =
                        result.response?.data?.customer?.addresses?.edges?.mapNotNull { it.node }
                    addresses?.forEach {
                        Log.d("Shopify", "Address:${it.address1}, ${it.city} ${it.country}")
                    }
                    if (addresses != null) {

                        onResult(addresses)
                    } else {
                        onResult(emptyList())
                    }
                }

                is GraphCallResult.Failure -> {
                    Log.e("Shopify", "Query failed: ${result.error.message}")
                    onResult(emptyList())

                }
            }
        }
    }

    suspend fun setDefaultAddress(
        addressId: ID
    ) {
        val userDocument = Firebase.firestore.collection("users").document(FirebaseAuth.getInstance().currentUser!!.uid).get().await()
        val customerToken = userDocument.getString("shopifyToken")
        Log.i("Token", "saveAddress: ${customerToken} ")

        val mutation = Storefront.mutation { root ->
            root.customerDefaultAddressUpdate(customerToken, addressId) { subQuery ->
                subQuery
                    .customer { customerQuery ->
                        customerQuery.defaultAddress {
                            it.firstName().lastName().address1().city().country().zip()
                        }
                    }
                    .userErrors { errorQuery ->
                        errorQuery.field().message()
                    }
            }
        }

        graphClient.mutateGraph(mutation).enqueue { result ->
            when (result) {
                is GraphCallResult.Success -> {
                    val payload = result.response.data?.customerDefaultAddressUpdate
                    val defaultAddress = payload?.customer?.defaultAddress
                    val errors = payload?.userErrors

                    if (defaultAddress != null) {
                        Log.d(
                            "TAG",
                            "Default address set to: ${defaultAddress.address1}, ${defaultAddress.city}"
                        )
                    } else {
                        errors?.forEach { error ->
                            Log.e(
                                "TAG",
                                "Field: ${error.field?.joinToString() ?: "Unknown"} - Message: ${error.message}"
                            )
                        } ?: Log.e("TAG", "error.")
                    }
                }

                is GraphCallResult.Failure -> {
                    Log.e("TAG", "Mutation failed: ${result.error.message}")
                }
            }
        }
    }

    suspend fun getDefaultAddress(
        onResult: (Storefront.MailingAddress) -> Unit
    ) {
        val userDocument = Firebase.firestore.collection("users").document(FirebaseAuth.getInstance().currentUser!!.uid).get().await()
        val customerToken = userDocument.getString("shopifyToken")
        Log.i("Token", "saveAddress: ${customerToken} ")

        val query = Storefront.query { root ->
            root.customer(customerToken) {
                it.defaultAddress {
                    it.firstName().lastName().address1().city().country().zip().phone()
                }
            }
        }

        graphClient.queryGraph(query).enqueue { result ->
            when (result) {
                is GraphCallResult.Success -> {
                    val defaultAddress = result.response.data?.customer?.defaultAddress
                    if (defaultAddress != null) {
                        Log.i(
                            "TAG",
                            "Default address: ${defaultAddress.address1}, ${defaultAddress.city}"
                        )
                        onResult(defaultAddress)
                    } else {
                        // getBack
                        Log.i("TAG", "no values.")
                    }
                }

                is GraphCallResult.Failure -> {
                    Log.i("TAG", "${result.error.message}")
                }
            }
        }
    }
}