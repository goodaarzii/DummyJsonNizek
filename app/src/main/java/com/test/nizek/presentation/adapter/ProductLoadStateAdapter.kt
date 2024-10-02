package com.test.nizek.presentation.adapter

import android.content.Context
import android.graphics.Color
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView
import com.test.nizek.R
import com.test.nizek.presentation.views.*

class ProductLoadStateAdapter : LoadStateAdapter<ProductLoadStateAdapter.LoadStateViewHolder>() {

    inner class LoadStateViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val progressBar: ProgressBar = itemView.findViewById(PROGRESS_BAR_ID)
        private val textView: TextView = itemView.findViewById(TEXT_VIEW_ID)

        fun bind(loadState: LoadState) {
            when (loadState) {
                is LoadState.Loading -> {
                    showView()
                }

                is LoadState.Error -> {
                    hideViews()

                }

                is LoadState.NotLoading -> {
                    hideViews()
                }
            }
        }

        private fun hideViews() {
            progressBar.visibility = View.GONE
            textView.visibility = View.GONE
        }

        private fun showView() {
            progressBar.visibility = View.VISIBLE
            textView.visibility = View.VISIBLE
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, loadState: LoadState): LoadStateViewHolder {
        val relativeLayout = setUpParent(parent.context)
        val progressBar = setupProgressBarView(parent.context)
        val textView = setupTextView(parent.context)


        relativeLayout.addView(progressBar)
        relativeLayout.addView(textView)

        return LoadStateViewHolder(relativeLayout)
    }

    override fun onBindViewHolder(holder: LoadStateViewHolder, loadState: LoadState) {
        holder.bind(loadState)
    }






    companion object {
        const val PROGRESS_BAR_ID = 153135
        const val TEXT_VIEW_ID = 153136
        const val PADDING_ITEM = 16
        const val ZERO = 0

    }
}
