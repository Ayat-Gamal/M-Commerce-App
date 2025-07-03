package com.example.m_commerce.features.categories.domain.entity

import com.example.m_commerce.features.categories.data.dto.CategoryDto

data class Category(val id: String,override val image: String?,override val name: String): DisplayableCategory

fun CategoryDto.toCategory(): Category = Category(id, image, name)