package com.example.m_commerce.features.home.domain.entity

import com.example.m_commerce.features.home.data.dto.CategoryDto

data class Category(val id: String, val image: String, val name: String)

fun CategoryDto.toCategory(): Category = Category(id, image, name)