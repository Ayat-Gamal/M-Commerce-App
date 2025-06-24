package com.example.m_commerce.features.AddressMangment.domain.entity

data class DeleteResponse(
    val deletedAddressId: String?,
    val customerUserErrors: List<Error> = emptyList(),
    val userErrors: List<Error> = emptyList()
) {
    val isSuccess: Boolean
        get() = deletedAddressId != null && customerUserErrors.isEmpty() && userErrors.isEmpty()

    data class Error(
        val field: List<String>?,
        val message: String?
    )
}