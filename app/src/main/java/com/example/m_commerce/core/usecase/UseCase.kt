package com.example.m_commerce.core.usecase

interface UseCase <in Params, out Result>{
      operator fun invoke(params: Params) : Result
}

