package com.example.m_commerce.features.categories.data.datasources.remote

import com.example.m_commerce.features.categories.data.dto.CategoryDto
import com.shopify.buy3.GraphClient
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import javax.inject.Inject

class CategoryRemoteDataSourceImpl @Inject constructor(client: GraphClient) : CategoryRemoteDataSource {
    override fun getCategories(): Flow<List<CategoryDto>> = callbackFlow {


    }

}