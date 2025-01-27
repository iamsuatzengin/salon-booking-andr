package com.zapplications.salonbooking.core.ui.otp

import android.content.Context
import android.graphics.Rect
import android.text.Editable
import android.text.TextWatcher
import android.util.AttributeSet
import android.view.KeyEvent
import androidx.appcompat.widget.AppCompatEditText
import com.zapplications.salonbooking.R
import com.zapplications.salonbooking.core.extensions.drawable
import com.zapplications.salonbooking.core.extensions.hideKeyboard

class OtpEditTextItem(
    context: Context,
    attrs: AttributeSet? = null,
) : AppCompatEditText(context, attrs) {

    var isFirstItem = false
    var isLastItem = false

    var previousItem: OtpEditTextItem? = null
    var nextItem: OtpEditTextItem? = null

    var isErrorState = false
    var onClearState: (() -> Unit)? = null

    private var textWatcher: TextWatcher = object : TextWatcher {
        override fun beforeTextChanged(text: CharSequence?, start: Int, count: Int, after: Int) {}

        override fun onTextChanged(text: CharSequence?, start: Int, before: Int, count: Int) {
            if (isFirstItem && text?.isNotEmpty() == true) {
                onClearState?.invoke()
                isErrorState = false
            }
            if (text.isNullOrEmpty().not()) {
                nextItem?.isEnabled = true
                nextItem?.requestFocus()
            }

            if (isLastItem && text?.isNotEmpty() == true) {
                context.hideKeyboard(this@OtpEditTextItem)
                clearFocus()
            }
        }

        override fun afterTextChanged(text: Editable?) = Unit
    }

    init {
        if (!isFirstItem) {
            onKeyListener()
        }

        addTextChangedListener(textWatcher)
    }

    override fun onFocusChanged(focused: Boolean, direction: Int, previouslyFocusedRect: Rect?) {
        super.onFocusChanged(focused, direction, previouslyFocusedRect)

        if (!isErrorState) {
            background = if (focused) {
                resources.drawable(R.drawable.bg_selected_otp_edit_text)
            } else {
                resources.drawable(R.drawable.bg_otp_edit_text)
            }
        }
    }

    private fun onKeyListener() {
        setOnKeyListener { _, i, keyEvent ->
            val isDelete = keyEvent.action == KeyEvent.ACTION_DOWN && i == KeyEvent.KEYCODE_DEL
            if (isDelete) {
                if (text?.isNotEmpty() == true) setText("")
                previousItem?.requestFocus()
            }
            false
        }
    }
}
