package com.example.m_commerce.features.categories.domain.usecases

import com.example.m_commerce.core.usecase.UseCase
import com.example.m_commerce.features.categories.domain.repository.CategoryRepository
import com.example.m_commerce.features.categories.domain.entity.Category
import com.example.m_commerce.features.categories.domain.entity.toCategory
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetCategoriesUseCase @Inject constructor(private val repo: CategoryRepository) : UseCase<Unit, Flow<List<Category>?>> {
    override fun invoke(params: Unit): Flow<List<Category>?> {
        return repo.getCategories().map { it.map { it.toCategory() } }
    }
}