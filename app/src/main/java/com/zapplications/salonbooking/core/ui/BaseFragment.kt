package com.zapplications.salonbooking.core.ui

import android.os.Bundle
import android.view.View
import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.zapplications.salonbooking.R
import com.zapplications.salonbooking.core.UiEvent
import com.zapplications.salonbooking.core.ui.applyinset.InsetSides
import com.zapplications.salonbooking.core.ui.applyinset.applySystemBarInsetsAsPadding
import com.zapplications.salonbooking.core.ui.bottomsheet.MyBottomSheet
import com.zapplications.salonbooking.core.ui.bottomsheet.MyBottomSheetParam
import com.zapplications.salonbooking.core.ui.dialog.statusdialog.ButtonConfig
import com.zapplications.salonbooking.core.ui.dialog.statusdialog.StatusDialog
import com.zapplications.salonbooking.core.ui.dialog.statusdialog.StatusDialogState
import com.zapplications.salonbooking.core.ui.dialog.statusdialog.statusDialogBuilder
import kotlinx.coroutines.launch

abstract class BaseFragment<VM : BaseViewModel>(@LayoutRes private val layoutResId: Int) :
    Fragment(layoutResId) {

    abstract val viewModel : VM
    private var statusDialog: StatusDialog? = null
    private var bottomSheet: MyBottomSheet? = null

    /**
     * This method is optional. You can use this method for collecting UI event.
     * Just override and collect shared flow. You don't need to launch with lifecycle in your fragments.
     */
    open suspend fun collectUiEvents() = Unit

    /**
     * This method is optional. You can use this method for collecting UI state.
     * Just override and collect state flow. You don't need to launch with lifecycle in your fragments.
     */
    open suspend fun collectUiStates() = Unit

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (canRootViewApplyInset()) {
            view.applySystemBarInsetsAsPadding(adjustRootViewInsetSides())
        }

        collectStates()
    }

    private fun collectStates() {
        lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    collectUiStates()
                }

                launch {
                    collectUiEvents()
                }

                launch {
                    viewModel.loadingState.collect { isLoading ->
                        if (isLoading) {
                            statusDialog = loadingDialog()
                        } else {
                            statusDialog?.dismiss()
                        }
                    }
                }

                launch {
                    viewModel.baseUiEvent.collect { event ->
                        eventHandler(event)
                    }
                }
            }
        }
    }

    private fun eventHandler(event: UiEvent) {
        when (event) {
            is ShowError -> {
                showErrorDialog(
                    title = "Error",
                    message = event.message,
                    buttons = listOf(
                        ButtonConfig(
                            title = "Close",
                            action = {
                                dismiss()
                                findNavController().navigateUp()
                            }
                        )
                    )
                )
            }
            is ShowApiError -> {
                showErrorDialog(
                    title = event.errorModel.title.orEmpty(),
                    message = event.errorModel.description.orEmpty(),
                    buttons = event.errorModel.buttons ?: listOf(
                        ButtonConfig(
                            title = "Close",
                            action = {
                                dismiss()
                                findNavController().navigateUp()
                            }
                        )
                    )
                )
            }
        }
    }

    private fun loadingDialog(): StatusDialog {
        return statusDialogBuilder {
            state(StatusDialogState.LOADING)
            title(getString(R.string.title_loading))
        }.show(
            childFragmentManager,
            "Loading TAG"
        )
    }

    private fun showErrorDialog(
        title: String,
        message: String,
        buttons: List<ButtonConfig> = emptyList(),
    ) {
        bottomSheet = MyBottomSheet.newInstance(
            MyBottomSheetParam(
                title = title,
                description = message,
                buttons = buttons,
                isCancellable = false
            )
        )

        bottomSheet?.show(childFragmentManager, "MyBottomSheet")
    }

    open fun canRootViewApplyInset(): Boolean = false
    open fun adjustRootViewInsetSides(): InsetSides = InsetSides()

    override fun onDestroy() {
        super.onDestroy()
        statusDialog = null
        bottomSheet = null
    }
}