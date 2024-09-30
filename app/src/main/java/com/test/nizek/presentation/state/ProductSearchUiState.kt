package com.test.nizek.presentation.state

sealed class ProductSearchUiState {
    data object Idle : ProductSearchUiState()
    data object Loading : ProductSearchUiState()
    data object Success : ProductSearchUiState()
    data class Error(val message: String) : ProductSearchUiState()
}
