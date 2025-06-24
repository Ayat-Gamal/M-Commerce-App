package com.example.m_commerce.features.profile.data.remote





import com.example.m_commerce.features.profile.domain.entity.LatestRatesResponse
import com.example.m_commerce.features.profile.domain.entity.SymbolResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface CurrencyApiService {
    @GET("symbols")
    suspend fun getSupportedSymbols(
        @Query("access_key") accessKey: String
    ): SymbolResponse

    @GET("latest")
    suspend fun getLatestRates(
        @Query("access_key") accessKey: String,
        @Query("symbols") symbols: String? = "USD"
    ): LatestRatesResponse
}

//import com.example.m_commerce.features.profile.domain.entity.LatestRatesResponse
//import com.example.m_commerce.features.profile.domain.entity.SymbolResponse
//import retrofit2.http.GET
//import retrofit2.http.Query
//
//interface CurrencyApiService {
//
//    @GET("symbols")
//    suspend fun getSupportedSymbols(
//        @Query("access_key") accessKey: String
//    ): SymbolResponse
//
//    @GET("latest")
//    suspend fun getLatestRates(
//        @Query("access_key") accessKey: String,
//        @Query("base") base: String? = null,
//        @Query("symbols") symbols: String? = null
//    ): LatestRatesResponse
//}
