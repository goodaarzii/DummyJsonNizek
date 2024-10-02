package com.test.nizek.data.repositoryImpl

import androidx.paging.PagingSource
import com.test.nizek.domin.repository.ProductRepository
import com.test.nizek.data.services.DummyJsonApi
import com.test.nizek.domin.model.Product
import javax.inject.Inject

class ProductRepositoryImpl @Inject constructor(
    private val api: DummyJsonApi
) : ProductRepository {

    override fun getSearchResults(query: String): PagingSource<Int, Product> {
        return ProductPagingSource(api, query)
    }
}
