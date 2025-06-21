package com.example.m_commerce.features.home.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.m_commerce.features.brand.ui_state.HomeUiState
import com.example.m_commerce.features.categories.domain.usecases.GetCategoriesUseCase
import com.example.m_commerce.features.brand.domain.usecases.GetBrandsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getBrandsUseCase: GetBrandsUseCase,
    private val getCategoriesUseCase: GetCategoriesUseCase
) : ViewModel() {

    private val _dataState: MutableStateFlow<HomeUiState> = MutableStateFlow(HomeUiState.Loading)
    val dataState: StateFlow<HomeUiState> = _dataState.asStateFlow()


    fun getHomeData() = viewModelScope.launch {
        _dataState.value = HomeUiState.Loading

        try {
            val brands = getBrandsUseCase(Unit).catch { emit(null) }.firstOrNull()
            val categories = getCategoriesUseCase(Unit).catch { emit(null) }.firstOrNull()

            //TODO: This might be a bad idea
            if (brands.isNullOrEmpty()) {
                _dataState.value = HomeUiState.Error("No Brands Found")
            }else if (categories.isNullOrEmpty()){
                _dataState.value = HomeUiState.Error("No Categories Found")
            } else {
                _dataState.value = HomeUiState.Success(brands, categories)
            }
        } catch (e: Exception) {
            Log.e("Error", e.message.toString())
            _dataState.value = HomeUiState.Error(e.message.toString())
        }
    }

}