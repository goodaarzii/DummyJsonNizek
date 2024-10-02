package com.test.nizek.presentation.fragments

import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import androidx.paging.PagingData
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.test.nizek.R
import com.test.nizek.domin.model.Product
import com.test.nizek.presentation.adapter.ProductAdapter
import com.test.nizek.presentation.adapter.ProductLoadStateAdapter
import com.test.nizek.presentation.state.ProductSearchIntent
import com.test.nizek.presentation.state.ProductSearchUiState
import com.test.nizek.presentation.viewModel.ProductSearchViewModel
import com.test.nizek.presentation.views.*
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ProductSearchFragment : Fragment() {

    private val viewModel: ProductSearchViewModel by viewModels()
    private lateinit var productAdapter: ProductAdapter

    private lateinit var recyclerView: RecyclerView
    private lateinit var progressBar: ProgressBar
    private lateinit var errorTextView: TextView
    private lateinit var searchEditText: EditText
    private lateinit var searchEditTextParent: RelativeLayout

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        val relativeLayout = getParentRelativeView(requireContext())

        initViews(relativeLayout)

        observeState()

        return relativeLayout
    }


    private fun initViews(parent: RelativeLayout) {
        val paddingInPx = dpToPx(PADDING_ITEM_8, parent.context)

        searchEditTextParent = getSearchEditTextParent(parent.context, paddingInPx)

        searchEditText = getSearchEditText(parent.context, paddingInPx)

        recyclerView = getHomeRecyclerView(parent.context)

        progressBar = getHomeProgressBarView(parent.context)

        errorTextView = getHomeErrorTextView(parent.context)

        searchEditTextParent.addView(searchEditText, getSearchEditTextParentParam())


        parent.addView(searchEditTextParent, getSearchEditTextViewParams())
        parent.addView(recyclerView, getRecyclerViewParams(searchEditTextParent))
        parent.addView(errorTextView, getErrorTextParams())
        parent.addView(progressBar, getHomeProgressBarViewParam())


        productAdapter = ProductAdapter()

        val gridLayoutManager = GridLayoutManager(context, TWO)
        gridLayoutManager.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
            override fun getSpanSize(position: Int): Int {
                return if (position % THREE == ZERO) TWO else ONE
            }
        }

        recyclerView.layoutManager = gridLayoutManager
        recyclerView.adapter = productAdapter.withLoadStateFooter(
            footer = ProductLoadStateAdapter()
        )


        searchEditText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (count > ZERO)
                    lifecycleScope.launch {
                        viewModel.processIntent(ProductSearchIntent.SearchQueryChanged(s.toString()))
                    }
            }

            override fun afterTextChanged(s: Editable?) {}
        })
    }


    private fun observeState() {
        lifecycleScope.launchWhenStarted {
            viewModel.uiState.collect { uiState ->
                handleUiState(uiState)
            }
        }
    }

    private fun handleUiState(state: ProductSearchUiState) {
        when (state) {
            is ProductSearchUiState.Loading -> {
                showLoading()

            }

            is ProductSearchUiState.Success -> {
                showContent(state.products)

            }

            is ProductSearchUiState.Error -> {
                showError(state.message)

            }

            is ProductSearchUiState.Idle -> {
                showIdleState()

            }
        }
    }

    private fun showIdleState() {
        errorTextView.visibility = View.VISIBLE
        progressBar.visibility = View.GONE
        recyclerView.visibility = View.GONE
    }

    private fun showLoading() {
        requireActivity().runOnUiThread {
            progressBar.visibility = View.VISIBLE
            recyclerView.visibility = View.GONE
            errorTextView.visibility = View.GONE
        }
    }

    private fun showError(message: String) {
        progressBar.visibility = View.GONE
        recyclerView.visibility = View.GONE
        errorTextView.text = message
        errorTextView.visibility = View.VISIBLE
    }

    private fun showContent(products: PagingData<Product>) {
        recyclerView.visibility = View.VISIBLE
        errorTextView.visibility = View.GONE

        lifecycleScope.launch {
            productAdapter.submitData(products)
        }


        productAdapter.addLoadStateListener { loadState ->
            val isListEmpty =
                loadState.refresh is LoadState.NotLoading && productAdapter.itemCount == ZERO
            val isLoading = loadState.refresh is LoadState.Loading
            val isError = loadState.refresh is LoadState.Error

            if (isLoading) {
                progressBar.visibility = View.VISIBLE
                recyclerView.visibility = View.GONE
            } else {
                progressBar.visibility = View.GONE
                recyclerView.visibility = View.VISIBLE
            }

            if (isError) {
                showError(
                    (loadState.refresh as LoadState.Error).error.localizedMessage
                        ?: getString(R.string.error_loading_data)
                )
            } else if (isListEmpty) {
                showError(getString(R.string.no_products_found))
            }
        }
    }

    private fun dpToPx(dp: Int, context: Context): Int {
        return (dp * context.resources.displayMetrics.density).toInt()
    }

    companion object {
        const val PADDING_ITEM_8 = 8
        const val ZERO = 0
        const val ONE = 1
        const val TWO = 2
        const val THREE = 3

    }
}
