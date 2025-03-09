package com.zapplications.salonbooking.core.ui.dialog.statusdialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat.getColorStateList
import androidx.core.view.isVisible
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import com.google.android.material.button.MaterialButton
import com.zapplications.salonbooking.R
import com.zapplications.salonbooking.core.extensions.ONE
import com.zapplications.salonbooking.core.extensions.TWO
import com.zapplications.salonbooking.core.extensions.ZERO
import com.zapplications.salonbooking.core.extensions.orTrue
import com.zapplications.salonbooking.core.extensions.orZero
import com.zapplications.salonbooking.core.extensions.parcelable
import com.zapplications.salonbooking.databinding.LayoutStatusDialogBinding

fun statusDialogBuilder(
    builder: StatusDialog.Builder.() -> Unit,
) = StatusDialog.Builder().apply(builder)

/**
 * Represents a stateful UI component for displaying feedback or messages to the user.
 *
 * This component can display an icon, a title, a description, and up to two buttons.
 *
 * **States:**
 * - `LOADING`: Indicates that an operation is in progress.
 * - `SUCCESS`: Indicates that an operation has completed successfully.
 * - `FAILED`: Indicates that an operation has failed.
 *
 * **Elements:**
 * - `iconType`: The type of icon to display, corresponding to one of the defined states.
 * - `title`: The main title text.
 * - `description`: A more detailed description or message.
 * - `buttons`: A list of up to two `ButtonConfig` objects.
 *
 * **Button Configuration (`ButtonConfig`):**
 * - `title`: The text displayed on the button.
 * - `action`: A lambda function to be executed when the button is clicked.
 *
 * **Behavior:**
 * - If the `buttons` list is empty, the button area is hidden.
 * - If the `buttons` list contains one or two `ButtonConfig` objects, the buttons are displayed vertically.
 * - Buttons list size cannot be more than 2.
 */
class StatusDialog : DialogFragment() {
    private var _binding: LayoutStatusDialogBinding? = null
    private val binding get() = _binding!!

    private var statusDialogParam: StatusDialogParam? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = LayoutStatusDialogBinding.inflate(inflater, container, false)
        dialog?.window?.setBackgroundDrawableResource(android.R.color.transparent)

        statusDialogParam = if (savedInstanceState == null) {
            arguments?.parcelable(STATUS_DIALOG_PARAM)
        } else {
            savedInstanceState.parcelable(STATUS_DIALOG_PARAM)
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initDialogState(state = statusDialogParam?.state)
        initTitle(title = statusDialogParam?.title)
        initDescription(description = statusDialogParam?.description)
        initButtons(buttons = statusDialogParam?.buttons)
    }

    private fun initDialogState(state: StatusDialogState?) {
        when (state) {
            StatusDialogState.LOADING -> {
                binding.progressIndicator.isVisible = true
                binding.btnPrimarySolid.isVisible = false
                binding.btnSecondaryOutlined.isVisible = false
            }

            StatusDialogState.SUCCESS -> {
                binding.ivSuccessful.isVisible = true
            }

            StatusDialogState.FAILED -> {
                binding.ivFailed.isVisible = true
                binding.dialogIconContainer.backgroundTintList =
                    getColorStateList(requireContext(), R.color.color_error)
            }

            else -> {
                binding.ivFailed.isVisible = true
                binding.dialogIconContainer.backgroundTintList =
                    getColorStateList(requireContext(), R.color.color_error)
            }
        }
    }

    private fun initTitle(title: String?) {
        binding.tvDialogTitle.text = title
    }

    private fun initDescription(description: String?) {
        binding.tvDialogDescription.text = description
    }

    private fun initButtons(buttons: List<ButtonConfig>?) {
        check(buttons?.size.orZero() <= TWO) {
            "Buttons list size cannot be more than 2"
        }

        binding.btnPrimarySolid.isVisible =
            statusDialogParam?.state != StatusDialogState.LOADING && buttons?.isNotEmpty() == true
        binding.btnSecondaryOutlined.isVisible =
            statusDialogParam?.state != StatusDialogState.LOADING && buttons?.size.orZero() > ONE

        val primaryButton = buttons?.getOrNull(ZERO)
        val secondaryButton = buttons?.getOrNull(ONE)

        primaryButton?.let {
            binding.btnPrimarySolid.configureButton(it)
        }

        secondaryButton?.let {
            binding.btnSecondaryOutlined.configureButton(it)
        }
    }

    private fun MaterialButton.configureButton(buttonConfig: ButtonConfig) {
        text = buttonConfig.title
        setOnClickListener { buttonConfig.action.invoke(this@StatusDialog) }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.apply {
            putParcelable(STATUS_DIALOG_PARAM, statusDialogParam)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        const val STATUS_DIALOG_PARAM = "STATUS_DIALOG_PARAM"
        fun newInstance(statusDialogParam: StatusDialogParam): StatusDialog {
            val args = Bundle().apply {
                putParcelable(STATUS_DIALOG_PARAM, statusDialogParam)
            }
            val dialog = StatusDialog()
            dialog.arguments = args
            return dialog
        }
    }

    class Builder {
        private var param: StatusDialogParam = StatusDialogParam()

        fun state(state: StatusDialogState): Builder {
            this.param = param.copy(state = state)
            return this
        }

        fun title(title: String): Builder {
            this.param = param.copy(title = title)
            return this
        }

        fun description(description: String): Builder {
            this.param = param.copy(description = description)
            return this
        }

        fun buttons(buttons: List<ButtonConfig>): Builder {
            this.param = param.copy(buttons = buttons)
            return this
        }

        fun isCancellable(isCancellable: Boolean): Builder {
            this.param = param.copy(isCancellable = isCancellable)
            return this
        }

        fun show(fragmentManager: FragmentManager, tag: String?): StatusDialog {
            val dialog = newInstance(param)
            dialog.isCancelable = param.isCancellable.orTrue()
            dialog.show(fragmentManager, tag)

            return dialog
        }
    }
}
