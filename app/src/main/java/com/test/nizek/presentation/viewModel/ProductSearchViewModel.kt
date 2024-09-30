package com.test.nizek.presentation.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.test.nizek.domin.model.Product
import com.test.nizek.domin.usecase.SearchProductsUseCase
import com.test.nizek.presentation.state.ProductSearchUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProductSearchViewModel @Inject constructor(
    private val searchProductsUseCase: SearchProductsUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow<ProductSearchUiState>(ProductSearchUiState.Idle)
    val uiState: StateFlow<ProductSearchUiState> = _uiState.asStateFlow()

    val pagingDataFlow = MutableStateFlow<PagingData<Product>>(PagingData.empty())

    fun searchProducts(query: String) {
        viewModelScope.launch {
            _uiState.value = ProductSearchUiState.Loading
            searchProductsUseCase(query).flow.cachedIn(viewModelScope)
                .collect { pagingData ->
                    pagingDataFlow.value = pagingData
                    _uiState.value = ProductSearchUiState.Success
                }
        }
    }
}
