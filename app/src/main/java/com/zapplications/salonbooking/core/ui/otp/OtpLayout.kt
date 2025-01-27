package com.zapplications.salonbooking.core.ui.otp

import android.content.Context
import android.util.AttributeSet
import android.view.Gravity.CENTER_HORIZONTAL
import android.view.LayoutInflater
import android.widget.LinearLayout
import com.zapplications.salonbooking.R
import com.zapplications.salonbooking.core.extensions.drawable
import com.zapplications.salonbooking.core.extensions.showKeyboard
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
        isFocusable = true
        isFocusableInTouchMode = true
        orientation = HORIZONTAL
        background = resources.drawable(R.drawable.bg_transparent_focused_state)
        gravity = CENTER_HORIZONTAL

        postDelayed({
            binding.et1.isEnabled = true
            binding.et1.requestFocus()
            context.showKeyboard(binding.et1)
        }, 200)

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

    fun setOtpText(otp: String?) {
        if (otp.isNullOrEmpty() || otp.length != 4) return

        binding.et1.setText(otp[0].toString())
        binding.et2.setText(otp[1].toString())
        binding.et3.setText(otp[2].toString())
        binding.et4.setText(otp[3].toString())
    }

    fun getOtpText(): String = buildString {
        append(binding.et1.text)
        append(binding.et2.text)
        append(binding.et3.text)
        append(binding.et4.text)
    }

    private fun fields() = listOf(binding.et1, binding.et2, binding.et3, binding.et4)

    fun hasAnyEmptyField() = fields().map { it.text }.any { it?.isEmpty() == true }

    fun onError() {
        fields().forEach { field ->
            field.isErrorState = true
            field.isEnabled = false

            field.background = resources.drawable(R.drawable.bg_error_otp_edit_text)

            field.text?.clear()
        }

        postDelayed({
            binding.et1.isEnabled = true
            binding.et1.requestFocus()
            context.showKeyboard(binding.et1)
        }, 500)
    }

    private fun clearErrorState() {
        fields().forEach { field ->
            field.isErrorState = false
            field.background = resources.drawable(R.drawable.bg_otp_edit_text)
        }
    }
}
