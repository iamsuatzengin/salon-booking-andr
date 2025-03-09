package com.zapplications.salonbooking.core.ui.dialog.statusdialog

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class StatusDialogParam(
    val title: String = "",
    val description: String = "",
    val buttons: List<ButtonConfig> = emptyList(),
    val state: StatusDialogState = StatusDialogState.LOADING,
    val isCancellable: Boolean = false,
) : Parcelable
