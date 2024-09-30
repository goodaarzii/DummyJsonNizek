package com.test.nizek.data.services

import com.test.nizek.domin.model.ProductSearchResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface DummyJsonApi {
    @GET("/products/search")
    suspend fun searchProducts(
        @Query("q") query: String
    ): ProductSearchResponse
}
