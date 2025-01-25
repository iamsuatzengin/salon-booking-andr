package com.zapplications.salonbooking.core.ui.otp

import android.content.Context
import android.util.AttributeSet
import android.view.Gravity.CENTER_HORIZONTAL
import android.view.LayoutInflater
import android.widget.LinearLayout
import com.zapplications.salonbooking.R
import com.zapplications.salonbooking.core.extensions.drawable
import com.zapplications.salonbooking.databinding.LayoutOtpBinding

class OtpLayout @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
) : LinearLayout(context, attrs, defStyleAttr) {
    private val binding = LayoutOtpBinding.inflate(LayoutInflater.from(context), this)

    init {
        initView()
    }

    private fun initView() {
        orientation = HORIZONTAL
        gravity = CENTER_HORIZONTAL

        with(binding) {
            et1.apply {
                isFirstItem = true
                nextItem = et2
                onClearState = { clearErrorState() }
            }

            et2.apply {
                previousItem = et1
                nextItem = et3
                onClearState = { clearErrorState() }
            }

            et3.apply {
                previousItem = et2
                nextItem = et4
                onClearState = { clearErrorState() }
            }

            et4.apply {
                isLastItem = true
                previousItem = et3
                onClearState = { clearErrorState() }
            }
        }
    }

    fun getOtpText(): String = buildString {
        append(binding.et1.text)
        append(binding.et2.text)
        append(binding.et3.text)
        append(binding.et4.text)
    }

    fun hasAnyEmptyField() = listOf(
        binding.et1.text,
        binding.et2.text,
        binding.et3.text,
        binding.et4.text
    ).any { it?.isEmpty() == true }

    fun errorState() {
        binding.et1.isErrorState = true
        binding.et1.background = resources.drawable(R.drawable.bg_error_otp_edit_text)
        binding.et2.isErrorState = true
        binding.et2.background = resources.drawable(R.drawable.bg_error_otp_edit_text)
        binding.et3.isErrorState = true
        binding.et3.background = resources.drawable(R.drawable.bg_error_otp_edit_text)
        binding.et4.isErrorState = true
        binding.et4.background = resources.drawable(R.drawable.bg_error_otp_edit_text)
    }

    fun clearErrorState() {
        binding.et1.isErrorState = false
        binding.et1.background = resources.drawable(R.drawable.bg_otp_edit_text)

        binding.et2.isErrorState = false
        binding.et2.background = resources.drawable(R.drawable.bg_otp_edit_text)
        binding.et3.isErrorState = false
        binding.et3.background = resources.drawable(R.drawable.bg_otp_edit_text)

        binding.et4.isErrorState = false
        binding.et4.background = resources.drawable(R.drawable.bg_otp_edit_text)
    }
}
