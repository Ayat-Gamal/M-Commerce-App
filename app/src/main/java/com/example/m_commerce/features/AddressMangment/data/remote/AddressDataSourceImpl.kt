package com.example.m_commerce.features.AddressMangment.data.remote

import android.util.Log
import com.example.m_commerce.features.AddressMangment.domain.entity.Address
import com.example.m_commerce.features.AddressMangment.domain.entity.DeleteResponse
import com.example.m_commerce.features.AddressMangment.domain.entity.Response
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.firestore
import com.shopify.buy3.GraphCallResult
import com.shopify.buy3.GraphClient
import com.shopify.buy3.Storefront
import com.shopify.graphql.support.ID
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException

class AddressDataSourceImpl @Inject constructor(
    private val graphClient: GraphClient,
    private val firestore: FirebaseFirestore,
    private val auth: FirebaseAuth
) : AddressDataSource {

    override suspend fun saveAddress(address: Address): Flow<Response<Unit>> = callbackFlow {
        try {
            trySend(Response.Loading)

            val userId =
                auth.currentUser?.uid ?: throw IllegalStateException("User not authenticated")
            val userDocument = firestore.collection("users").document(userId).get().await()
            val customerToken = userDocument.getString("shopifyToken")
                ?: throw IllegalStateException("Shopify token not found")

            val addressInput = Storefront.MailingAddressInput().apply {
                firstName = address.firstName
                lastName = address.lastName
                address1 = address.address1
                address2 = address.address2
                city = address.city
                country = address.country
                zip = address.zip
                phone = address.phone
            }

            val mutation = Storefront.mutation { root ->
                root.customerAddressCreate(customerToken, addressInput) { payload ->
                    payload.customerAddress { addr ->
                        addr.firstName()
                        addr.lastName()
                        addr.address1()
                        addr.address2()
                        addr.city()
                        addr.country()
                        addr.zip()
                        addr.phone()
                    }
                    payload.customerUserErrors { errors ->
                        errors.field()
                        errors.message()
                    }
                    payload.userErrors { errors ->
                        errors.field()
                        errors.message()
                    }
                }
            }

            graphClient.mutateGraph(mutation).enqueue { result ->
                when (result) {
                    is GraphCallResult.Success -> {
                        val payload = result.response.data?.customerAddressCreate
                        val errors = (payload?.customerUserErrors ?: emptyList()) +
                                (payload?.userErrors ?: emptyList())

                        if (errors.isEmpty()) {
                            trySend(Response.Success(Unit))
                        } else {
                            trySend(Response.Error(errors.joinToString {
                                it.message ?: "Unknown error"
                            }))
                        }
                    }

                    is GraphCallResult.Failure -> {
                        trySend(Response.Error(result.error.message ?: "Mutation failed"))
                    }
                }
                close()
            }

            awaitClose()
        } catch (e: Exception) {
            trySend(Response.Error(e.message ?: "An unexpected error occurred"))
            close()
        }
    }

    override suspend fun getAddresses(): Flow<Response<List<Address>>> = callbackFlow {
        trySend(Response.Loading)

        try {
            val userId = auth.currentUser?.uid ?: throw Exception("User not authenticated")
            val userDoc = firestore.collection("users").document(userId).get().await()
            val customerToken =
                userDoc.getString("shopifyToken") ?: throw Exception("Shopify token not found")

            val query = Storefront.query { root ->
                root.customer(customerToken) { customer ->
                    customer.addresses({ args -> args.first(150) }) { connection ->
                        connection.edges { edge ->
                            edge.node { address ->
                                address
                                    .firstName()
                                    .lastName()
                                    .address1()
                                    .address2()
                                    .city()
                                    .country()
                                    .zip()
                                    .phone()
                            }
                        }
                    }
                }
            }

            graphClient.queryGraph(query).enqueue { result ->
                when (result) {
                    is GraphCallResult.Success -> {
                        val addresses =
                            result.response.data?.customer?.addresses?.edges?.mapNotNull { it.node }
                                ?.map { it.toAddress() } ?: emptyList()
                        trySend(Response.Success(addresses))
                    }

                    is GraphCallResult.Failure -> {
                        trySend(Response.Error(result.error.message ?: "Query failed"))
                    }
                }

            }

            awaitClose { /* Handle cancellation */ }
        } catch (e: Exception) {
            trySend(Response.Error(e.message ?: "Unknown error"))
            close()
        }
    }

    override suspend fun setDefaultAddress(addressId: String): Flow<Response<Unit>> = callbackFlow {
        trySend(Response.Loading)
        try {
            val userId = FirebaseAuth.getInstance().currentUser?.uid
                ?: throw Exception("User not authenticated")
            val userDoc = Firebase.firestore.collection("users").document(userId).get().await()
            val customerToken = userDoc.getString("shopifyToken")
                ?: throw Exception("Shopify token not found")

            val shopifyId = ID(addressId)
            val mutation = Storefront.mutation { root ->
                root.customerDefaultAddressUpdate(customerToken, shopifyId) { payload ->
                    payload.customer { customer ->
                        customer.defaultAddress { address ->
                            address
                                .firstName()
                                .lastName()
                                .address1()
                                .city()
                                .country()
                                .zip()
                                .phone()
                        }
                    }
                    payload.userErrors { errors ->
                        errors.field()
                        errors.message()
                    }
                }
            }

            graphClient.mutateGraph(mutation).enqueue { result ->
                when (result) {
                    is GraphCallResult.Success -> {
                        val payload = result.response.data?.customerDefaultAddressUpdate
                        val errors = payload?.userErrors

                        if (errors.isNullOrEmpty()) {
                            trySend(Response.Success(Unit))
                        } else {
                            Log.i("TAG", "setDefaultAddress:  ${errors}  ${errors.joinToString()}")
                            val errorMsg = errors.joinToString { it.message ?: "Unknown error" }
                            trySend(Response.Error("Failed to set default address: $errorMsg"))
                        }
                    }

                    is GraphCallResult.Failure -> {
                        trySend(Response.Error(result.error.message ?: "Mutation failed"))
                    }
                }
                close()
            }

            awaitClose { /* Handle cancellation */ }
        } catch (e: Exception) {
            trySend(Response.Error(e.message ?: "Failed to set default address"))
            close()
        }
    }

    override suspend fun getDefaultAddress(): Flow<Response<Address>> = callbackFlow {
        trySend(Response.Loading)

        try {
            val userId = FirebaseAuth.getInstance().currentUser?.uid
                ?: throw Exception("User not authenticated")
            val userDoc = Firebase.firestore.collection("users").document(userId).get().await()
            val customerToken = userDoc.getString("shopifyToken")
                ?: throw Exception("Shopify token not found")

            val query = Storefront.query { root ->
                root.customer(customerToken) { customer ->
                    customer.defaultAddress { address ->
                        address
                            .firstName()
                            .lastName()
                            .address1()
                            .address2()
                            .city()
                            .country()
                            .zip()
                            .phone()
                    }
                }
            }

            graphClient.queryGraph(query).enqueue { result ->
                when (result) {
                    is GraphCallResult.Success -> {
                        val address = result.response.data?.customer?.defaultAddress?.toAddress()
                        if (address != null) {
                            trySend(Response.Success(address))
                        } else {
                            trySend(Response.Error("No default address found"))
                        }
                    }

                    is GraphCallResult.Failure -> {
                        trySend(Response.Error(result.error.message ?: "Query failed"))
                    }
                }
                close()
            }

            awaitClose { /* Handle cancellation */ }
        } catch (e: Exception) {
            trySend(Response.Error(e.message ?: "Failed to get default address"))
            close()
        }
    }

    override suspend fun deleteAddress(addressId: String): Flow<Response<DeleteResponse>> =
        callbackFlow {
            trySend(Response.Loading)

            try {
                val userId = auth.currentUser?.uid ?: throw Exception("User not authenticated")
                val userDoc = firestore.collection("users").document(userId).get().await()
                val customerToken = userDoc.getString("shopifyToken")
                    ?: throw Exception("Shopify token not found")

                val mutation = Storefront.mutation { root ->
                    root.customerAddressDelete(
                        ID(addressId),
                        customerToken,
                        { payload ->
                            payload.deletedCustomerAddressId()
                            payload.customerUserErrors { errors ->
                                errors.field()
                                errors.message()
                            }
                            payload.userErrors { errors ->
                                errors.field()
                                errors.message()
                            }
                        }
                    )
                }

                graphClient.mutateGraph(mutation).enqueue { result ->
                    when (result) {
                        is GraphCallResult.Success -> {
                            val payload = result.response.data?.customerAddressDelete
                            val response = DeleteResponse(
                                deletedAddressId = payload?.deletedCustomerAddressId?.toString(),
                                customerUserErrors = payload?.customerUserErrors?.map {
                                    DeleteResponse.Error(it.field?.toList(), it.message)
                                } ?: emptyList(),
                                userErrors = payload?.userErrors?.map {
                                    DeleteResponse.Error(it.field?.toList(), it.message)
                                } ?: emptyList()
                            )
                            trySend(Response.Success(response))
                        }

                        is GraphCallResult.Failure -> {
                            trySend(
                                Response.Error(
                                    "Failed to delete address: ${result.error.message}"
                                )
                            )
                        }
                    }
                    close()
                }

                awaitClose { /* Handle cancellation */ }
            } catch (e: Exception) {
                trySend(
                    Response.Error(
                        "Delete failed: ${e.message ?: "Unknown error"}"
                    )
                )
                close()
            }
        }

    override fun getCustomerName(): Flow<String> = flow {
        val userId = FirebaseAuth.getInstance().currentUser?.uid
            ?: throw Exception("User not authenticated")

        val userDoc = Firebase.firestore.collection("users").document(userId).get().await()
        val customerToken = userDoc.getString("shopifyToken")
            ?: throw Exception("Shopify token not found")

        val query = Storefront.query { root ->
            root.customer(customerToken) { customer ->
                customer.firstName()
            }
        }

        val customerName = suspendCancellableCoroutine { cont ->
            graphClient.queryGraph(query).enqueue { result ->
                when (result) {
                    is GraphCallResult.Success -> {
                        val name = result.response.data?.customer?.firstName ?: "Anonymous"
                        cont.resume(name)
                    }

                    is GraphCallResult.Failure -> {
                        cont.resumeWithException(
                            result.error.cause ?: Exception("Unknown GraphQL error")
                        )
                    }
                }
            }
        }

        emit(customerName)
    }

    private fun Storefront.MailingAddress.toAddress(): Address {
        return Address(
            id = id.toString(),
            firstName = firstName ?: "",
            lastName = lastName ?: "",
            address1 = address1 ?: "",
            address2 = address2 ?: "",
            city = city ?: "",
            zip = zip ?: "",
            country = country ?: "",
            phone = phone
        )
    }
}