package com.zapplications.salonbooking.core.ui.dialog.statusdialog

import android.os.Parcelable
import androidx.fragment.app.DialogFragment
import kotlinx.parcelize.Parcelize

@Parcelize
class ButtonConfig(
    val title: String,
    val action: DialogFragment.() -> Unit,
) : Parcelable
