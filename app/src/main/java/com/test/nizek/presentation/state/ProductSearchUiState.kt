package com.test.nizek.presentation.state

import androidx.paging.PagingData
import com.test.nizek.domin.model.Product

sealed class ProductSearchUiState {
    data object Idle : ProductSearchUiState()
    data object Loading : ProductSearchUiState()
    data class Success(val products: PagingData<Product>) : ProductSearchUiState()
    data class Error(val message: String) : ProductSearchUiState()
}
