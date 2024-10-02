package com.test.nizek.presentation.adapter

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.test.nizek.R
import com.test.nizek.databinding.ProductItemBinding
import com.test.nizek.domin.model.Product


class ProductAdapter : PagingDataAdapter<Product, ProductAdapter.ProductViewHolder>(ProductDiffCallback()) {

    class ProductDiffCallback : DiffUtil.ItemCallback<Product>() {
        override fun areItemsTheSame(oldItem: Product, newItem: Product): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Product, newItem: Product): Boolean {
            return oldItem == newItem
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        // Dynamically create ConstraintLayout
        val constraintLayout = ConstraintLayout(parent.context).apply {
            layoutParams = ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            )
            setPadding(16, 16, 16, 16)
        }

        // Create ImageView programmatically
        val productImageView = ImageView(parent.context).apply {
            id = View.generateViewId()
            layoutParams = ConstraintLayout.LayoutParams(
                ConstraintLayout.LayoutParams.MATCH_PARENT,
                0 // Height set to 0, but will be constrained to maintain aspect ratio
            ).apply {
                dimensionRatio = "H,1:1" // Maintain 1:1 aspect ratio (height based on width)
                topToTop = ConstraintLayout.LayoutParams.PARENT_ID
                startToStart = ConstraintLayout.LayoutParams.PARENT_ID
                endToEnd = ConstraintLayout.LayoutParams.PARENT_ID
            }
            scaleType = ImageView.ScaleType.CENTER_CROP
        }

        // Create TextView programmatically
        val titleTextView = TextView(parent.context).apply {
            id = View.generateViewId()
            layoutParams = ConstraintLayout.LayoutParams(
                ConstraintLayout.LayoutParams.MATCH_PARENT,
                ConstraintLayout.LayoutParams.WRAP_CONTENT
            ).apply {
                topToBottom = productImageView.id
                startToStart = ConstraintLayout.LayoutParams.PARENT_ID
                endToEnd = ConstraintLayout.LayoutParams.PARENT_ID
            }
            text = "Title"
            setTextColor(Color.WHITE)
        }

        // Add views to ConstraintLayout
        constraintLayout.addView(productImageView)
        constraintLayout.addView(titleTextView)

        // Return ProductViewHolder
        return ProductViewHolder(constraintLayout, titleTextView, productImageView)
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        val product = getItem(position) ?: return
        holder.bind(product)
    }

    inner class ProductViewHolder(
        itemView: View,
        private val titleTextView: TextView,
        private val productImageView: ImageView
    ) : RecyclerView.ViewHolder(itemView) {
        fun bind(product: Product) {
            titleTextView.text = product.title
            val urlImage = "https://dummyjson.com/image/400x300/008080/ffffff?text=${product.title}"
            productImageView.load(urlImage) {
                crossfade(true)
                placeholder(R.drawable.ic_launcher_background)
            }
        }
    }
}





