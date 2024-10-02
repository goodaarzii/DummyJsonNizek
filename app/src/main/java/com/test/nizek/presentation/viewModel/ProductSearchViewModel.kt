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
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.consumeAsFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProductSearchViewModel @Inject constructor(
    private val searchProductsUseCase: SearchProductsUseCase
) : ViewModel() {


    private val _intentFlow = MutableSharedFlow<ProductSearchIntent>()


    private val _uiState = MutableStateFlow<ProductSearchUiState>(ProductSearchUiState.Idle)
    val uiState: StateFlow<ProductSearchUiState> = _uiState.asStateFlow()


    private val _searchQuery = MutableStateFlow("")

    init {
        observeSearchQuery()
        handleIntents()
    }


    private fun handleIntents() {
        viewModelScope.launch {
            _intentFlow.collect { intent ->
                when (intent) {
                    is ProductSearchIntent.SearchQueryChanged -> {
                        onSearchQueryChanged(intent.query)
                    }
                }
            }
        }
    }

    private fun onSearchQueryChanged(query: String) {
        _searchQuery.value = query
    }

    private fun observeSearchQuery() {
        viewModelScope.launch {
            _searchQuery
                .debounce(100) // Wait 100 ms before sending the request
                .distinctUntilChanged() // Only process unique queries
                .collect { query ->
                    if (query.isNotBlank()) {
                        searchProducts(query)  // Process valid queries
                    }
                }
        }
    }

    private fun searchProducts(query: String) {
        viewModelScope.launch {
            _uiState.value = ProductSearchUiState.Loading

            delay(1000)
            searchProductsUseCase(query).flow.cachedIn(viewModelScope)
                .collect { pagingData ->

                    _uiState.value =
                        ProductSearchUiState.Success(pagingData)

                }
        }
    }



    fun processIntent(intent: ProductSearchIntent) {
        viewModelScope.launch {
            _intentFlow.emit(intent)
        }
    }
}
