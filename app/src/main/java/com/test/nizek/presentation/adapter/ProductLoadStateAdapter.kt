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

    private fun setupProgressBarView(context: Context): ProgressBar {
        return ProgressBar(context).apply {
            id = PROGRESS_BAR_ID
            layoutParams = RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.WRAP_CONTENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT
            ).apply {
                addRule(RelativeLayout.CENTER_IN_PARENT)
                setMargins(0, 16, 0, 16)
            }
            isIndeterminate = true
        }
    }

    private fun setupTextView(context: Context): TextView {
        return TextView(context).apply {
            id = TEXT_VIEW_ID
            layoutParams = RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.WRAP_CONTENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT
            ).apply {
                addRule(RelativeLayout.ALIGN_PARENT_START)
                setMargins(0, 16, 0, 0)
            }
            text = context.getString(R.string.please_wait)
            textAlignment = View.TEXT_ALIGNMENT_CENTER

        }
    }

    private fun setUpParent(context: Context): RelativeLayout {
        return RelativeLayout(context).apply {
            layoutParams = ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            )
            setBackgroundColor(Color.LTGRAY)
        }
    }


    companion object {
        const val PROGRESS_BAR_ID = 153135
        const val TEXT_VIEW_ID = 153136
    }
}
