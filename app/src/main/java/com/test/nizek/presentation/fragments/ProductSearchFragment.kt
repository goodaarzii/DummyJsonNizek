package com.test.nizek.presentation.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.test.nizek.R
import com.test.nizek.databinding.FragmentProductSearchBinding
import com.test.nizek.presentation.adapter.ProductAdapter
import com.test.nizek.presentation.state.ProductSearchIntent
import com.test.nizek.presentation.state.ProductSearchUiState
import com.test.nizek.presentation.viewModel.ProductSearchViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ProductSearchFragment : Fragment(R.layout.fragment_product_search) {

    private val viewModel: ProductSearchViewModel by viewModels()
    private lateinit var binding : FragmentProductSearchBinding
    private lateinit var productAdapter: ProductAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentProductSearchBinding.bind(view)
        productAdapter = ProductAdapter()

        binding.recycler.adapter = productAdapter
        binding.recycler.layoutManager = LinearLayoutManager(requireContext())


        // Observe UI state changes
        lifecycleScope.launchWhenStarted {
            viewModel.uiState.collect { uiState ->
                handleUiState(uiState)
            }
        }

        // Setup search input field to send intents
//        binding.searchInput.doOnTextChanged { text, _, _, _ ->
//            lifecycleScope.launch {
//                viewModel.intents.send(ProductSearchIntent.SearchQueryChanged(text.toString()))
//            }
//        }

        CoroutineScope(Dispatchers.Main).launch {
            delay(1000)
            viewModel.testQuery("")
        }
    }

    private fun handleUiState(state: ProductSearchUiState) {
        when (state) {
            is ProductSearchUiState.Loading -> {
                // Show loading UI
            }
            is ProductSearchUiState.Success -> {
                // Hide loading UI
                Log.e("TAG", "handleUiState: ${state.products}" )
                CoroutineScope(Dispatchers.Main).launch {

                    productAdapter.submitData(state.products)
                }

            }
            is ProductSearchUiState.Error -> {
                // Show error UI
            }
            is ProductSearchUiState.Idle -> {
                // Do nothing
            }
        }
    }

    companion object {

        @JvmStatic
        fun newInstance() = ProductSearchFragment()
    }
}