package com.test.nizek.presentation.fragments

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.paging.PagingData
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.test.nizek.R
import com.test.nizek.databinding.FragmentProductSearchBinding
import com.test.nizek.domin.model.Product
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
class ProductSearchFragment : Fragment() {

    private val viewModel: ProductSearchViewModel by viewModels()
    private lateinit var productAdapter: ProductAdapter

    lateinit var recyclerView: RecyclerView
    lateinit var progressBar: ProgressBar
    lateinit var errorTextView: TextView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        val relativeLayout = RelativeLayout(requireContext()).apply {
            layoutParams = RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.MATCH_PARENT,
                RelativeLayout.LayoutParams.MATCH_PARENT
            )
        }

        initViews(relativeLayout)


        return relativeLayout
    }

    private fun initViews(parent: RelativeLayout) {
        // Create the RecyclerView
        recyclerView = RecyclerView(requireContext()).apply {
            id = View.generateViewId()
            layoutManager = LinearLayoutManager(requireContext())
        }

        // Create the ProgressBar
        progressBar = ProgressBar(requireContext()).apply {
            id = View.generateViewId()
            isIndeterminate = true
        }

        // Create the Error TextView
        errorTextView = TextView(requireContext()).apply {
            id = View.generateViewId()
            text = "Error Occurred"
            visibility = View.GONE
            gravity = Gravity.CENTER
            textSize = 18f
            setTextColor(Color.RED)
        }

        // Add views to the RelativeLayout
        parent.addView(
            recyclerView, RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.MATCH_PARENT,
                RelativeLayout.LayoutParams.MATCH_PARENT
            )
        )

        // Center ProgressBar in the layout
        val progressBarParams = RelativeLayout.LayoutParams(
            RelativeLayout.LayoutParams.WRAP_CONTENT,
            RelativeLayout.LayoutParams.WRAP_CONTENT
        ).apply {
            addRule(RelativeLayout.CENTER_IN_PARENT)
        }
        parent.addView(progressBar, progressBarParams)

        // Center Error TextView in the layout
        val errorTextParams = RelativeLayout.LayoutParams(
            RelativeLayout.LayoutParams.WRAP_CONTENT,
            RelativeLayout.LayoutParams.WRAP_CONTENT
        ).apply {
            addRule(RelativeLayout.CENTER_IN_PARENT)
        }
        parent.addView(errorTextView, errorTextParams)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        observeState()

        initIntents()

        initView()


    }

    private fun initView() {
        productAdapter = ProductAdapter()

        recyclerView.adapter = productAdapter
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

    }

    private fun observeState() {
        lifecycleScope.launchWhenStarted {
            viewModel.uiState.collect { uiState ->
                handleUiState(uiState)
            }
        }
    }

    private fun initIntents() {
        viewModel.processIntent(ProductSearchIntent.SearchQueryChanged(""))
    }

    private fun handleUiState(state: ProductSearchUiState) {
        when (state) {
            is ProductSearchUiState.Loading -> {
                Log.e("TAG", "handleUiState: loading")
                showLoading()
            }

            is ProductSearchUiState.Success -> {
                // Hide loading UI
                Log.e("TAG", "handleUiState: ${state.products}")
                CoroutineScope(Dispatchers.Main).launch {
                    showContent(state.products)
//                    productAdapter.submitData(state.products)
                }

            }

            is ProductSearchUiState.Error -> {
                showError(state.message)
            }

            is ProductSearchUiState.Idle -> {
                // Do nothing
            }
        }
    }

    fun showLoading() {
        progressBar.visibility = View.VISIBLE
        recyclerView.visibility = View.GONE
        errorTextView.visibility = View.GONE
    }

    // Show error state UI
    fun showError(message: String) {
        progressBar.visibility = View.GONE
        recyclerView.visibility = View.GONE
        errorTextView.text = message
        errorTextView.visibility = View.VISIBLE
    }

    // Show content UI state with the products
    fun showContent(products: PagingData<Product>) {
        progressBar.visibility = View.GONE
        recyclerView.visibility = View.VISIBLE
        errorTextView.visibility = View.GONE


        CoroutineScope(Dispatchers.Main).launch {

            productAdapter.submitData(products)
        }
    }

    companion object {

        @JvmStatic
        fun newInstance() = ProductSearchFragment()
    }
}