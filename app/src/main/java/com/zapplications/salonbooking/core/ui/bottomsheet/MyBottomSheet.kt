package com.zapplications.salonbooking.core.ui.bottomsheet

import android.os.Bundle
import android.os.Parcelable
import android.view.View
import androidx.core.view.isVisible
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.zapplications.salonbooking.R
import com.zapplications.salonbooking.core.extensions.ONE
import com.zapplications.salonbooking.core.extensions.ZERO
import com.zapplications.salonbooking.core.extensions.parcelable
import com.zapplications.salonbooking.core.ui.dialog.statusdialog.ButtonConfig
import com.zapplications.salonbooking.core.viewBinding
import com.zapplications.salonbooking.databinding.LayoutMyBottomSheetBinding
import kotlinx.parcelize.Parcelize

class MyBottomSheet: BottomSheetDialogFragment(R.layout.layout_my_bottom_sheet) {
    private val binding by viewBinding(LayoutMyBottomSheetBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        arguments?.parcelable<MyBottomSheetParam>(MY_BOTTOM_SHEET_PARAM)?.let { param ->
            initView(param)
        }
    }

    private fun initView(param: MyBottomSheetParam) = with(binding) {
        tvBottomSheetTitle.text = param.title
        tvBottomSheetSubtitle.text = param.subtitle
        tvBottomsheetDescription.text = param.description

        param.buttons.getOrNull(ZERO)?.let {
            btnPrimarySolid.isVisible = true
            btnPrimarySolid.text = it.title
            btnPrimarySolid.setOnClickListener { _ ->
                it.action.invoke(this@MyBottomSheet)
            }
        }

        param.buttons.getOrNull(ONE)?.let {
            btnSecondaryOutlined.isVisible = true
            btnSecondaryOutlined.text = it.title
            btnSecondaryOutlined.setOnClickListener { _ ->
                it.action.invoke(this@MyBottomSheet)
            }
        }

        isCancelable = param.isCancellable
    }

    companion object {
        const val MY_BOTTOM_SHEET_PARAM = "MY_BOTTOM_SHEET_PARAM"
        fun newInstance(param: MyBottomSheetParam): MyBottomSheet = MyBottomSheet().apply {
            arguments = Bundle().apply {
                putParcelable(MY_BOTTOM_SHEET_PARAM, param)
            }
        }
    }
}

@Parcelize
data class MyBottomSheetParam(
    val title: String,
    val subtitle: String? = null,
    val description: String,
    val isCancellable: Boolean = true,
    val buttons: List<ButtonConfig>
) : Parcelable