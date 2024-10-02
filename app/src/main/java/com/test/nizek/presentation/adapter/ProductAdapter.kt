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

    private fun getTitleTextview(context: Context?, productImageView: ImageView): TextView {
        return TextView(context).apply {
            id = View.generateViewId()
            layoutParams = ConstraintLayout.LayoutParams(
                ConstraintLayout.LayoutParams.MATCH_PARENT,
                ConstraintLayout.LayoutParams.WRAP_CONTENT
            ).apply {
                topToBottom = productImageView.id
                startToStart = ConstraintLayout.LayoutParams.PARENT_ID
                endToEnd = ConstraintLayout.LayoutParams.PARENT_ID
                setMargins(PADDING_ITEM)
            }

            setTextColor(Color.WHITE)
        }
    }

    private fun getImageView(context: Context): ImageView {
        return ImageView(context).apply {
            id = View.generateViewId()
            layoutParams = ConstraintLayout.LayoutParams(
                ConstraintLayout.LayoutParams.MATCH_PARENT,
                ZERO
            ).apply {
                dimensionRatio = IMAGE_RATIO
                topToTop = ConstraintLayout.LayoutParams.PARENT_ID
                startToStart = ConstraintLayout.LayoutParams.PARENT_ID
                endToEnd = ConstraintLayout.LayoutParams.PARENT_ID
            }
            scaleType = ImageView.ScaleType.CENTER_CROP
        }
    }

    private fun getConstraintParent(context: Context): ConstraintLayout {
        return ConstraintLayout(context).apply {
            layoutParams = ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            )
            setPadding(PADDING_ITEM, PADDING_ITEM, PADDING_ITEM, PADDING_ITEM)
        }
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





