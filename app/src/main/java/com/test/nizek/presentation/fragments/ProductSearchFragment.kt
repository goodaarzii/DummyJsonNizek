package com.test.nizek.presentation.fragments

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.Gravity
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import androidx.paging.PagingData
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.test.nizek.R
import com.test.nizek.databinding.FragmentProductSearchBinding
import com.test.nizek.domin.model.Product
import com.test.nizek.presentation.adapter.ProductAdapter
import com.test.nizek.presentation.adapter.ProductLoadStateAdapter
import com.test.nizek.presentation.state.ProductSearchIntent
import com.test.nizek.presentation.state.ProductSearchUiState
import com.test.nizek.presentation.theme.Purple40
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

    private lateinit var recyclerView: RecyclerView
    private lateinit var progressBar: ProgressBar
    private lateinit var errorTextView: TextView
    private lateinit var searchEditText: EditText

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        val relativeLayout = RelativeLayout(requireContext()).apply {
            layoutParams = RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.MATCH_PARENT,
                RelativeLayout.LayoutParams.MATCH_PARENT
            ).apply {
                setBackgroundColor(ContextCompat.getColor(requireContext(),R.color.orange))
            }
        }

        initViews(relativeLayout)
        observeState()

        return relativeLayout
    }

    private fun initViews(parent: RelativeLayout) {
        val paddingInPx = dpToPx(8, parent.context)
        // Create a RelativeLayout that will act as the parent for the EditText
        val searchEditTextParent = RelativeLayout(requireContext()).apply {
            id = View.generateViewId()
            setBackgroundColor(ContextCompat.getColor(parent.context,R.color.purple_700)) // Set background color of the parent (blue)
            layoutParams = RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.MATCH_PARENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT
            ).apply {
                setPadding(paddingInPx, paddingInPx, paddingInPx, paddingInPx) // Add margins if needed

            }
        }

        // Create Search EditText for entering search queries inside the parent
        searchEditText = EditText(requireContext()).apply {
            id = View.generateViewId()
            hint = "Type to search..."
            setHintTextColor(ContextCompat.getColor(parent.context,R.color.black))
            layoutParams = RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.MATCH_PARENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT
            ).apply {

                setPadding(paddingInPx, paddingInPx, paddingInPx, paddingInPx)
                setMargins(paddingInPx, paddingInPx, paddingInPx, paddingInPx)
            }
            textSize = 18F
            setTextColor(ContextCompat.getColor(parent.context,R.color.black))

        }

        // Add the EditText inside the parent RelativeLayout
        searchEditTextParent.addView(
            searchEditText, RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.MATCH_PARENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT
            )
        )

        // Add the parent layout (with the EditText inside it) to the main parent layout
        parent.addView(
            searchEditTextParent, RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.MATCH_PARENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT
            )
        )

        // Other views (RecyclerView, ProgressBar, errorTextView) stay unchanged below:
        recyclerView = RecyclerView(requireContext()).apply {
            id = View.generateViewId()
            layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
            visibility = View.GONE // Initially hidden
        }

        progressBar = ProgressBar(requireContext()).apply {
            id = View.generateViewId()
            isIndeterminate = true
            visibility = View.GONE
            setBackgroundColor(Color.LTGRAY)
        }

        errorTextView = TextView(requireContext()).apply {
            id = View.generateViewId()
            text = context.getString(R.string.try_to_type_something)
            visibility = View.VISIBLE // Initially visible
            gravity = Gravity.CENTER
            textSize = 18f
            setTextColor(Color.RED)
        }

        parent.addView(
            recyclerView, RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.MATCH_PARENT,
                RelativeLayout.LayoutParams.MATCH_PARENT
            ).apply {
                addRule(RelativeLayout.BELOW, searchEditTextParent.id)
            }
        )

        val progressBarParams = RelativeLayout.LayoutParams(
            RelativeLayout.LayoutParams.WRAP_CONTENT,
            RelativeLayout.LayoutParams.WRAP_CONTENT
        ).apply {
            addRule(RelativeLayout.CENTER_IN_PARENT)
        }

        val errorTextParams = RelativeLayout.LayoutParams(
            RelativeLayout.LayoutParams.WRAP_CONTENT,
            RelativeLayout.LayoutParams.WRAP_CONTENT
        ).apply {
            addRule(RelativeLayout.CENTER_IN_PARENT)
        }

        parent.addView(errorTextView, errorTextParams)
        parent.addView(progressBar, progressBarParams)

        productAdapter = ProductAdapter()
        recyclerView.adapter = productAdapter.withLoadStateFooter(
            footer = ProductLoadStateAdapter()
        )

        // Handle search input
        searchEditText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (count > 0)
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
                Log.e("eee", "handleUiState: load" )
                showLoading()
            }

            is ProductSearchUiState.Success -> {
                Log.e("eee", "handleUiState: success" )
                showContent(state.products)

            }

            is ProductSearchUiState.Error -> {
                Log.e("eee", "handleUiState: error" )
                showError(state.message)
            }

            is ProductSearchUiState.Idle -> {
                Log.e("eee", "handleUiState: idle" )
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
        progressBar.visibility = View.GONE
        recyclerView.visibility = View.VISIBLE
        errorTextView.visibility = View.GONE

        lifecycleScope.launch {
            productAdapter.submitData(products)
        }

        productAdapter.addLoadStateListener { loadState ->
            if (loadState.refresh is LoadState.NotLoading && productAdapter.itemCount == 0) {
                showError(getString(R.string.no_products_found))
            }
        }
    }
    fun dpToPx(dp: Int, context: Context): Int {
        return (dp * context.resources.displayMetrics.density).toInt()
    }
}
