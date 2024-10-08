package com.test.nizek.presentation.state

sealed class ProductSearchIntent {
    data class SearchQueryChanged(val query: String) : ProductSearchIntent()
}
