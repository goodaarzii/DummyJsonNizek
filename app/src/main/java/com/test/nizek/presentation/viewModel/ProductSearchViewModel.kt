package com.test.nizek.presentation.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.test.nizek.domin.model.Product
import com.test.nizek.domin.usecase.SearchProductsUseCase
import com.test.nizek.presentation.state.ProductSearchIntent
import com.test.nizek.presentation.state.ProductSearchUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.SendChannel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.consumeAsFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProductSearchViewModel @Inject constructor(
    private val searchProductsUseCase: SearchProductsUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow<ProductSearchUiState>(ProductSearchUiState.Idle)
    val uiState: StateFlow<ProductSearchUiState> = _uiState.asStateFlow()

    // Mutable StateFlow for the search query
    private val _searchQuery = MutableStateFlow("")

    init {
        observeSearchQuery()
    }

    private fun observeSearchQuery() {
        viewModelScope.launch {
            _searchQuery
                .debounce(100)  // Debounce for 100 ms
                .distinctUntilChanged()  // Only process unique queries
                .collect { query ->
                    searchProducts(query)
                }
        }
    }

    fun onSearchQueryChanged(query: String) {
        _searchQuery.value = query
    }

    fun testQuery(query: String) {
        viewModelScope.launch {
            _searchQuery.value = query  // Directly sets the query to test the API call
        }
    }

    private fun searchProducts(query: String) {
        viewModelScope.launch {
            _uiState.value = ProductSearchUiState.Loading
            searchProductsUseCase(query).flow.cachedIn(viewModelScope)
                .collect { pagingData ->
                    _uiState.value = ProductSearchUiState.Success(pagingData)  // Emit products list here
                }
        }
    }

    fun processIntent(any: Any) {


    }
}
