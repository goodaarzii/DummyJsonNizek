package com.test.nizek.presentation.views

import android.content.Context
import android.graphics.Color
import android.view.Gravity
import android.view.View
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.test.nizek.R

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
        setBackgroundColor(ContextCompat.getColor(context,R.color.purple_700))
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





