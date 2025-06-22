package com.example.m_commerce.core.utils.extentions

import com.example.m_commerce.features.brand.data.dto.BrandDto
import com.shopify.buy3.GraphCallResult
import com.shopify.buy3.Storefront

fun  Storefront.CollectionConnection.toDomain(): List<BrandDto>? {
    return this.nodes?.mapNotNull {
            BrandDto(it.id.toString(), it.image?.url, it.title)
        }
}