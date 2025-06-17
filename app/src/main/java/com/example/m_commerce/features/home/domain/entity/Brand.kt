package com.example.m_commerce.features.home.domain.entity

import com.example.m_commerce.features.home.data.dto.BrandDto

data class Brand(val id: Int, val image: String, val name: String)

fun BrandDto.toBrand(): Brand = Brand(id, image, name)