package com.example.m_commerce.features.brand.domain.entity

import com.example.m_commerce.features.brand.data.dto.BrandDto
import com.example.m_commerce.features.categories.domain.entity.DisplayableCategory
import kotlinx.serialization.Serializable

@Serializable
data class Brand(val id: String?,override val image: String?,override val name: String?) : DisplayableCategory

fun BrandDto.toBrand(): Brand = Brand(id, image, name)