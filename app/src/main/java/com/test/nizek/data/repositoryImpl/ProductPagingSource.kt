package com.test.nizek.data.repositoryImpl

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.test.nizek.data.services.DummyJsonApi
import com.test.nizek.domin.model.Product
import kotlinx.coroutines.delay

class ProductPagingSource(
    private val api: DummyJsonApi,
    private val query: String
) : PagingSource<Int, Product>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Product> {
        return try {
            val currentPage = params.key ?: 0 // Start with skip=0
            val response = api.searchProducts(query, limit = params.loadSize, skip = currentPage)

            LoadResult.Page(
                data = response.products.orEmpty(),
                prevKey = if (currentPage == 0) null else currentPage - params.loadSize,
                nextKey = if (response.products.orEmpty().isEmpty()) null else currentPage + params.loadSize
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, Product>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(state.config.pageSize) ?: anchorPage?.nextKey?.minus(state.config.pageSize)
        }
    }
}



