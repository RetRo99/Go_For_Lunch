package com.retar.go4lunch.widget

import android.content.Context
import android.text.SpannableStringBuilder
import android.util.AttributeSet
import android.view.View
import android.widget.FrameLayout
import androidx.core.widget.doOnTextChanged
import com.retar.go4lunch.R
import com.retar.go4lunch.utils.hideKeyboard
import com.retar.go4lunch.utils.showKeyboard
import kotlinx.android.synthetic.main.view_autocomplete_widget.view.*

class AutocompleteWidget(context: Context, attrs: AttributeSet?) : FrameLayout(context, attrs) {

    init {

        View.inflate(context, R.layout.view_autocomplete_widget, this)
        search_close_btn.setOnClickListener {
            search_edittext.text = SpannableStringBuilder("")
            visibility = View.INVISIBLE
        }


    }

    fun doOnTextChanged(
        action: (
            text: CharSequence?,
            start: Int,
            count: Int,
            after: Int
        ) -> Unit
    ) {
        search_edittext.doOnTextChanged(action)

    }


    override fun setVisibility(visibility: Int) {
        search_holder.visibility = visibility
        if (visibility == View.VISIBLE) {
            isClickable = true
            isFocusable = true
            search_edittext.showKeyboard()
        } else {
            isClickable = false
            isFocusable = false
            hideKeyboard()
        }
    }

}

