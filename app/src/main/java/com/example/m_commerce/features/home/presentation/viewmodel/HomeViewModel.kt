package com.example.m_commerce.features.home.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.m_commerce.features.home.domain.usecases.GetBrandsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val getBrandsUseCase: GetBrandsUseCase) : ViewModel() {

    init {
        viewModelScope.launch {
            try {

                getBrandsUseCase(Unit).collect{
                    it.size
                    Log.d("QL", "SIZE: ${it.size}")
                }
            } catch (e: Exception) {
                Log.e("QL", "ERRORRRRRRRRRRRRRRRR: ",e )
            }

        }
    }

    fun hamada (){}

}