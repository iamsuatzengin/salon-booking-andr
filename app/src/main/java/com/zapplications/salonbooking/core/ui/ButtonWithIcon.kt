package com.zapplications.salonbooking.core.ui

import android.content.Context
import android.content.res.ColorStateList
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.RelativeLayout
import androidx.core.content.res.ResourcesCompat
import com.zapplications.salonbooking.R
import com.zapplications.salonbooking.databinding.LayoutButtonWithIconBinding

class ButtonWithIcon @JvmOverloads constructor(
    context: Context,
    private val attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
) : RelativeLayout(context, attrs, defStyleAttr) {
    private val binding =
        LayoutButtonWithIconBinding.inflate(LayoutInflater.from(context), this, true)

    var buttonText: String = ""
        set(value) {
            field = value
            binding.buttonText.text = value
        }

    init {
        initView()
    }

    private fun initView() {
        context.obtainStyledAttributes(attrs, R.styleable.ButtonWithIcon).use {

            buttonText = it.getString(R.styleable.ButtonWithIcon_buttonText) ?: ""
            val buttonIconDrawable = it.getDrawable(R.styleable.ButtonWithIcon_buttonIcon)
            val buttonTextColor = it.getColor(
                R.styleable.ButtonWithIcon_buttonTextColor,
                ResourcesCompat.getColor(resources, R.color.black, null)
            )
            val buttonBackgroundColor = it.getColor(
                R.styleable.ButtonWithIcon_buttonBackgroundColor,
                resources.getColor(R.color.color_main, null)
            )
            binding.buttonIcon.setImageDrawable(buttonIconDrawable)
            binding.buttonText.setTextColor(buttonTextColor)
            binding.root.backgroundTintList = ColorStateList.valueOf(buttonBackgroundColor)
        }
    }
}
