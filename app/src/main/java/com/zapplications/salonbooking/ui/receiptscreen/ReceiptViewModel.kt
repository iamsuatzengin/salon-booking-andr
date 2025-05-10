package com.zapplications.salonbooking.ui.receiptscreen

import android.graphics.Bitmap
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.zapplications.salonbooking.core.qrCode
import com.zapplications.salonbooking.core.ui.BaseViewModel
import com.zapplications.salonbooking.core.ui.ShowError
import com.zapplications.salonbooking.domain.model.BookingAppointmentUiModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ReceiptViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
) : BaseViewModel() {
    val bookingAppointmentUiModel = savedStateHandle.get<BookingAppointmentUiModel>(
        BOOKING_APPOINTMENT_UI_MODEL
    )

    private val _uiState = MutableStateFlow<Bitmap?>(null)
    val uiState = _uiState.asStateFlow()

    fun generateQrCodeBitmap() {
        viewModelScope.launch {
            val bitmap = bookingAppointmentUiModel.qrCode()

            bitmap?.let { b ->
                _uiState.update { b }
            } ?: run {
                sendEvent(ShowError("Qr code could not be generated"))
            }
        }
    }

    companion object {
        const val BOOKING_APPOINTMENT_UI_MODEL = "bookingAppointmentUiModel"
    }
}
