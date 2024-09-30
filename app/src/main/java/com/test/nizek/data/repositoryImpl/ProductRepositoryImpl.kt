package com.test.nizek.data.repositoryImpl

import com.test.nizek.data.repository.ProductRepository
import com.test.nizek.data.services.DummyJsonApi
import com.test.nizek.domin.model.Product
import javax.inject.Inject

class ProductRepositoryImpl @Inject constructor(
    private val api: DummyJsonApi
) : ProductRepository {
    override suspend fun searchProducts(query: String): List<Product> {
        val response = api.searchProducts(query)
        return response.products.orEmpty()
    }
}

