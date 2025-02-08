package com.zapplications.salonbooking.core.ui.dialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.zapplications.salonbooking.databinding.LayoutCustomDialogBinding

class CustomDialog : DialogFragment() {
    private var _binding: LayoutCustomDialogBinding? = null
    private val binding get() = _binding!!

    private var title: String? = null
    private var description: String? = null
    private var positiveButtonText: String? = null
    private var negativeButtonText: String? = null
    private var onPositiveAction: ((DialogFragment) -> Unit)? = null
    private var onNegativeAction: ((DialogFragment) -> Unit)? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = LayoutCustomDialogBinding.inflate(inflater, container, false)
        dialog?.window?.setBackgroundDrawableResource(android.R.color.transparent)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initView()
    }

    private fun initView() = with(binding) {
        tvDialogTitle.text = title
        tvDialogBody.text = description
        btnPositive.text = positiveButtonText
        btnNegative.text = negativeButtonText

        btnPositive.setOnClickListener { onPositiveAction?.invoke(this@CustomDialog) }
        btnNegative.setOnClickListener { onNegativeAction?.invoke(this@CustomDialog) }
    }

    fun title(text: String): CustomDialog {
        title = text
        return this
    }

    fun description(text: String): CustomDialog {
        description = text
        return this
    }

    fun positiveButton(text: String, action: DialogFragment.() -> Unit, ): CustomDialog {
        positiveButtonText = text
        onPositiveAction = action
        return this
    }

    fun negativeButton(text: String, action: DialogFragment.() -> Unit, ): CustomDialog {
        negativeButtonText = text
        onNegativeAction = action
        return this
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
