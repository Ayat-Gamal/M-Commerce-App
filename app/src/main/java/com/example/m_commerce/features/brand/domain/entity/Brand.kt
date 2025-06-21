package com.example.m_commerce.features.brand.domain.entity

import com.example.m_commerce.features.brand.data.dto.BrandDto
import kotlinx.serialization.Serializable

@Serializable
data class Brand(val id: String?, val image: String?, val name: String?)

fun BrandDto.toBrand(): Brand = Brand(id, image, name)