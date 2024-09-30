package com.test.nizek.domin.model


import com.google.gson.annotations.SerializedName

data class ProductSearchResponse(
    @SerializedName("limit")
    val limit: Int? = null,
    @SerializedName("products")
    val products: List<Product>? = null,
    @SerializedName("skip")
    val skip: Int? = null,
    @SerializedName("total")
    val total: Int? = null
)