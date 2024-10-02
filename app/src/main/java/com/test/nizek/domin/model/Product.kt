package com.test.nizek.domin.model


import com.google.gson.annotations.SerializedName

data class Product(
    @SerializedName("id")
    val id: Int? = null,
    @SerializedName("price")
    val price: Double? = null,
    @SerializedName("title")
    val title: String? = null
) {
    fun getImageUrl() = "https://dummyjson.com/image/400x300/008080/ffffff?text=${title}"
}