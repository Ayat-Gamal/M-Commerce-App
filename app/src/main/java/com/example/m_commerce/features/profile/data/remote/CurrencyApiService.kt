package com.example.m_commerce.features.profile.data.remote





import com.example.m_commerce.features.profile.domain.entity.ApiConversionRateResponse
import com.example.m_commerce.features.profile.domain.entity.ApiSymbolResponse
import retrofit2.http.GET
import retrofit2.http.Path

interface CurrencyApiService {

    @GET("{apiKey}/codes")
    suspend fun getSupportedSymbols(
        @Path("apiKey") apiKey: String
    ): ApiSymbolResponse

    @GET("{apiKey}/pair/{base}/{target}")
    suspend fun getLatestRates(
        @Path("apiKey") apiKey: String,
        @Path("base") base: String = "EGP",
        @Path("target") target: String
    ): ApiConversionRateResponse
}

//interface CurrencyApiService {
//    @GET("codes")
//    suspend fun getSupportedSymbols(
//        @Query("access_key") accessKey: String
//    ): SymbolResponse
//
//    @GET("latest")
//    suspend fun getLatestRates(
//        @Query("access_key") accessKey: String,
//        @Query("symbols") symbols: String? = "USD"
//    ): LatestRatesResponse
//}







