package com.zapplications.salonbooking.core.coroutineflow

import com.zapplications.salonbooking.core.ui.dialog.statusdialog.ButtonConfig

class AppException(val errorModel: ApiErrorModel): Exception()

data class ApiErrorModel(
    val title: String? = null,
    val description: String? = null,
    val buttons: List<ButtonConfig>? = null
) {
    companion object {
        val authenticationCommonError = ApiErrorModel(
            title = "Authentication Error",
            description = "Something went wrong. Please try again later."
        )
    }
}
