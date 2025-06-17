package com.example.m_commerce.features.home.domain.entity

import com.example.m_commerce.features.home.data.dto.BrandDto
import kotlinx.serialization.Serializable

@Serializable
data class Brand(val id: String, val image: String, val name: String)

fun BrandDto.toBrand(): Brand = Brand(id, image, name)