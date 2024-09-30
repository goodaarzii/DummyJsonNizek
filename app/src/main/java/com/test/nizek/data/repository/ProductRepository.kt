package com.test.nizek.data.repository

import com.test.nizek.domin.model.Product

interface ProductRepository {
    suspend fun searchProducts(query: String): List<Product>
}

