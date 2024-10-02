package com.test.nizek.presentation.views

import android.content.Context
import android.graphics.Color
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.core.view.setMargins
import androidx.recyclerview.widget.RecyclerView
import com.test.nizek.R
import com.test.nizek.presentation.adapter.ProductAdapter.Companion.IMAGE_RATIO
import com.test.nizek.presentation.adapter.ProductAdapter.Companion.PADDING_ITEM
import com.test.nizek.presentation.adapter.ProductAdapter.Companion.ZERO
import com.test.nizek.presentation.adapter.ProductLoadStateAdapter
import com.test.nizek.presentation.adapter.ProductLoadStateAdapter.Companion
import com.test.nizek.presentation.adapter.ProductLoadStateAdapter.Companion.PROGRESS_BAR_ID
import com.test.nizek.presentation.adapter.ProductLoadStateAdapter.Companion.TEXT_VIEW_ID

fun getParentRelativeView(context: Context): RelativeLayout {
    return RelativeLayout(context).apply {
        layoutParams = RelativeLayout.LayoutParams(
            RelativeLayout.LayoutParams.MATCH_PARENT,
            RelativeLayout.LayoutParams.MATCH_PARENT
        ).apply {
            setBackgroundColor(ContextCompat.getColor(context, R.color.orange))
        }
    }
}

fun getSearchEditTextParent(context: Context,paddingInPx:Int): RelativeLayout {
    return RelativeLayout(context).apply {
        id = View.generateViewId()
        setBackgroundColor(ContextCompat.getColor(context,R.color.purple_light))
        layoutParams = RelativeLayout.LayoutParams(
            RelativeLayout.LayoutParams.MATCH_PARENT,
            RelativeLayout.LayoutParams.WRAP_CONTENT
        ).apply {
            setPadding(paddingInPx, paddingInPx, paddingInPx, paddingInPx)

        }
    }
}

fun getSearchEditText(context: Context,paddingInPx:Int): EditText {
    return  EditText(context).apply {
        id = View.generateViewId()
        hint = "Type to search..."
        setHintTextColor(ContextCompat.getColor(context,R.color.black))
        layoutParams = RelativeLayout.LayoutParams(
            RelativeLayout.LayoutParams.MATCH_PARENT,
            RelativeLayout.LayoutParams.WRAP_CONTENT
        ).apply {

            setPadding(paddingInPx, paddingInPx, paddingInPx, paddingInPx)
            setMargins(paddingInPx, paddingInPx, paddingInPx, paddingInPx)
        }
        textSize = 18F
        setTextColor(ContextCompat.getColor(context,R.color.black))

    }
}

fun getHomeRecyclerView(context: Context): RecyclerView {
    return RecyclerView(context).apply {
        id = View.generateViewId()
    }
}

fun getHomeProgressBarView(context: Context): ProgressBar {
    return ProgressBar(context).apply {
        id = View.generateViewId()
        isIndeterminate = true
        visibility = View.GONE

    }
}
fun getHomeErrorTextView(context: Context): TextView {
    return TextView(context).apply {
        id = View.generateViewId()
        text = context.getString(R.string.try_to_type_something)
        visibility = View.VISIBLE
        gravity = Gravity.CENTER
        textSize = 18f
        setTextColor(Color.RED)
    }
}

fun getHomeProgressBarViewParam(): RelativeLayout.LayoutParams {
    return RelativeLayout.LayoutParams(
        RelativeLayout.LayoutParams.WRAP_CONTENT,
        RelativeLayout.LayoutParams.WRAP_CONTENT
    ).apply {
        addRule(RelativeLayout.CENTER_IN_PARENT)
    }
}
fun getSearchEditTextParentParam(): RelativeLayout.LayoutParams {
    return  RelativeLayout.LayoutParams(
        RelativeLayout.LayoutParams.MATCH_PARENT,
        RelativeLayout.LayoutParams.WRAP_CONTENT
    )
}
 fun getSearchEditTextViewParams() = RelativeLayout.LayoutParams(
    RelativeLayout.LayoutParams.MATCH_PARENT,
    RelativeLayout.LayoutParams.WRAP_CONTENT

)


fun getRecyclerViewParams(searchEditTextParent:View): RelativeLayout.LayoutParams {
    return RelativeLayout.LayoutParams(
        RelativeLayout.LayoutParams.MATCH_PARENT,
        RelativeLayout.LayoutParams.MATCH_PARENT
    ).apply {
        addRule(RelativeLayout.BELOW, searchEditTextParent.id)
    }
}

fun getErrorTextParams(): RelativeLayout.LayoutParams {
    return RelativeLayout.LayoutParams(
        RelativeLayout.LayoutParams.WRAP_CONTENT,
        RelativeLayout.LayoutParams.WRAP_CONTENT
    ).apply {
        addRule(RelativeLayout.CENTER_IN_PARENT)
    }
}

fun getConstraintParent(context: Context): ConstraintLayout {
    return ConstraintLayout(context).apply {
        layoutParams = ViewGroup.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
        setPadding(PADDING_ITEM, PADDING_ITEM, PADDING_ITEM, PADDING_ITEM)
    }
}

fun getTitleTextview(context: Context?, productImageView: ImageView): TextView {
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

fun getImageView(context: Context): ImageView {
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

fun setupProgressBarView(context: Context): ProgressBar {
    return ProgressBar(context).apply {
        id = PROGRESS_BAR_ID
        layoutParams = RelativeLayout.LayoutParams(
            RelativeLayout.LayoutParams.WRAP_CONTENT,
            RelativeLayout.LayoutParams.WRAP_CONTENT
        ).apply {
            addRule(RelativeLayout.CENTER_IN_PARENT)
            setMargins(
                ProductLoadStateAdapter.ZERO,
                ProductLoadStateAdapter.PADDING_ITEM,
                ProductLoadStateAdapter.ZERO,
                ProductLoadStateAdapter.PADDING_ITEM
            )
        }
        isIndeterminate = true
    }
}

fun setupTextView(context: Context): TextView {
    return TextView(context).apply {
        id = TEXT_VIEW_ID
        layoutParams = RelativeLayout.LayoutParams(
            RelativeLayout.LayoutParams.WRAP_CONTENT,
            RelativeLayout.LayoutParams.WRAP_CONTENT
        ).apply {
            addRule(RelativeLayout.ALIGN_PARENT_START)
            setMargins(
                ProductLoadStateAdapter.ZERO,
                ProductLoadStateAdapter.PADDING_ITEM,
                ProductLoadStateAdapter.ZERO,
                ProductLoadStateAdapter.ZERO
            )
        }
        text = context.getString(R.string.please_wait)
        textAlignment = View.TEXT_ALIGNMENT_CENTER

    }
}

 fun setUpParent(context: Context): RelativeLayout {
    return RelativeLayout(context).apply {
        layoutParams = ViewGroup.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
        setBackgroundColor(Color.LTGRAY)
    }
}





