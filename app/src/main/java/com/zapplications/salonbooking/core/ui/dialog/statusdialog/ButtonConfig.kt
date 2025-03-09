package com.zapplications.salonbooking.core.ui.dialog.statusdialog

import androidx.fragment.app.DialogFragment

class ButtonConfig(
    val title: String,
    val action: DialogFragment.() -> Unit,
)
