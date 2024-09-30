package com.test.nizek.domin.usecase

import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.test.nizek.data.repository.ProductRepository
import com.test.nizek.domin.model.Product
import javax.inject.Inject

class SearchProductsUseCase @Inject constructor(
    private val repository: ProductRepository
) {
    operator fun invoke(query: String): Pager<Int, Product> {
        return Pager(
            config = PagingConfig(
                pageSize = 20, // Define the size of each page
                enablePlaceholders = false
            ),
            pagingSourceFactory = { repository.getSearchResults(query) }
        )
    }
}

