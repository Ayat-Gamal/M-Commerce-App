package com.example.m_commerce.features.brand.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.m_commerce.features.brand.domain.usecases.GetBrandsUseCase
import com.example.m_commerce.features.brand.presentation.ui_state.BrandsUiState
import com.example.m_commerce.features.home.presentation.ui_state.HomeUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BrandsViewModel @Inject constructor(private val getBrandsUseCase: GetBrandsUseCase) : ViewModel() {

    private val _dataState: MutableStateFlow<BrandsUiState> = MutableStateFlow(BrandsUiState.Loading)
    val dataState: StateFlow<BrandsUiState> = _dataState.asStateFlow()


    init {
        getBrandsData()
    }

    private fun getBrandsData() = viewModelScope.launch {

        try {
            val brands = getBrandsUseCase(30).catch { emit(null) }.firstOrNull()

            if (brands.isNullOrEmpty()) {
                _dataState.value = BrandsUiState.Error("No Brands Found")
            } else {
                _dataState.value = BrandsUiState.Success(brands)
            }
        } catch (e: Exception) {
            Log.e("Error", e.message.toString())
            _dataState.value = BrandsUiState.Error(e.message.toString())
        }
    }
}