package com.example.m_commerce.core.utils.extentions

import com.example.m_commerce.features.brand.data.dto.BrandDto
import com.shopify.buy3.GraphCallResult
import com.shopify.buy3.Storefront

fun GraphCallResult<Storefront.QueryRoot>.getBrandListOrNull(): List<BrandDto>? {
    return (this as? GraphCallResult.Success)
        ?.response?.data?.collections?.nodes?.mapNotNull {
            BrandDto(it.id.toString(), it.image?.url, it.title)
        }
}