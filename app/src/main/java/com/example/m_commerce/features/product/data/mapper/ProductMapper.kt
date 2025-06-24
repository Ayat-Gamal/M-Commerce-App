package com.example.m_commerce.features.product.data.mapper

import com.example.m_commerce.features.product.domain.entities.Product
import com.shopify.buy3.Storefront

fun Storefront.Product.toDomain(): Product {
    val firstVariant = variants.edges.firstOrNull()?.node
    val price = firstVariant?.price?.amount.toString()
    //val priceAmount = price?.amount ?: "0.00"
    //val currencyCode = price?.currencyCode?.name ?: "EGP"
    val images: List<String> = images.edges.mapNotNull { it.node.url }

    val variantList = variants.edges
        .map { it.node }

    val sizes = variantList
        .flatMap { it.selectedOptions }
        .filter { it.name == "Size" }
        .map { it.value }
        .distinct()

    val colors = variantList
        .flatMap { it.selectedOptions }
        .filter { it.name == "Color" }
        .map { it.value }
        .distinct()


    return Product(
        id = id.toString(),
        title = title.orEmpty(),
        description = description.orEmpty(),
        images = images,
        price = price,
        currencyCode = firstVariant?.price?.currencyCode?.name ?: "EGP",
        category = productType,
        sizes = sizes,
        colors = colors,
        variants = variantList
    )
}

fun Storefront. ProductEdge.toDomain(): Product {
   return this.node!!.toDomain()
}
