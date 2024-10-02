package com.test.nizek.presentation.adapter

import android.content.Context
import android.graphics.Color
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.setMargins
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.test.nizek.R
import com.test.nizek.domin.model.Product
import com.test.nizek.presentation.views.*


class ProductAdapter :
    PagingDataAdapter<Product, ProductAdapter.ProductViewHolder>(ProductDiffCallback()) {

    class ProductDiffCallback : DiffUtil.ItemCallback<Product>() {
        override fun areItemsTheSame(oldItem: Product, newItem: Product): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Product, newItem: Product): Boolean {
            return oldItem == newItem
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {

        val constraintLayout = getConstraintParent(parent.context)

        val productImageView = getImageView(parent.context)

        val titleTextView = getTitleTextview(parent.context,productImageView)

        constraintLayout.addView(productImageView)
        constraintLayout.addView(titleTextView)

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
            val urlImage = product.getImageUrl()
            productImageView.load(urlImage) {
                crossfade(true)
                placeholder(R.drawable.ic_launcher_background)
            }
        }
    }

    companion object {
        const val PADDING_ITEM = 16
        const val IMAGE_RATIO = "H,1:1"
        const val ZERO = 0
    }
}





