package com.test.nizek.presentation.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.test.nizek.databinding.ProductItemBinding
import com.test.nizek.domin.model.Product


class ProductAdapter : PagingDataAdapter<Product, ProductAdapter.ProductViewHolder>(ProductDiffCallback()) {

    // ViewHolder for product items
    inner class ProductViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        // Bind product information like title, etc.
        fun bind(product: Product) {
            // Assuming the itemView is dynamically created with title and image
            val titleTextView = itemView.findViewById<TextView>(TITLE_VIEW_ID)
            val productImageView = itemView.findViewById<ImageView>(IMAGE_VIEW_ID)

            titleTextView.text = product.title
            // Load the image dynamically with your preferred image loading library (e.g., Glide, Coil)
        }
    }

    companion object {
        val TITLE_VIEW_ID: Int = View.generateViewId()
        val IMAGE_VIEW_ID: Int = View.generateViewId()
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        // Create the item layout dynamically
        val relativeLayout = RelativeLayout(parent.context).apply {
            layoutParams = ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            )
        }

        // Add a TextView for the product title
        val titleTextView = TextView(parent.context).apply {
            id = TITLE_VIEW_ID
            layoutParams = RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.WRAP_CONTENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT
            ).apply {
                addRule(RelativeLayout.ALIGN_PARENT_START)
                marginStart = 16
            }
        }
        relativeLayout.addView(titleTextView)

        // Add an ImageView for the product image
        val productImageView = ImageView(parent.context).apply {
            id = IMAGE_VIEW_ID
            layoutParams = RelativeLayout.LayoutParams(
                200, 200 // Image size
            ).apply {
                addRule(RelativeLayout.ALIGN_PARENT_END)
                marginEnd = 16
            }
        }
        relativeLayout.addView(productImageView)

        return ProductViewHolder(relativeLayout)
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        val product = getItem(position) ?: return
        holder.bind(product)
    }


    class ProductDiffCallback : DiffUtil.ItemCallback<Product>() {
        override fun areItemsTheSame(oldItem: Product, newItem: Product): Boolean {
            // Assuming product ID is unique for each product
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Product, newItem: Product): Boolean {
            // Compare content for updates
            return oldItem == newItem
        }
    }
}

