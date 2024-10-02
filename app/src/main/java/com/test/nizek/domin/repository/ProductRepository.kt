package com.test.nizek.domin.repository

import androidx.paging.PagingSource
import com.test.nizek.domin.model.Product

interface ProductRepository {
    fun getSearchResults(query: String): PagingSource<Int, Product>
}


