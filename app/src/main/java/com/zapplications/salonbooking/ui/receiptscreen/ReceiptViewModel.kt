package com.zapplications.salonbooking.ui.receiptscreen

import android.graphics.Bitmap
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zapplications.salonbooking.core.qrCode
import com.zapplications.salonbooking.domain.model.BookingAppointmentUiModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ReceiptViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
) : ViewModel() {
    val bookingAppointmentUiModel = savedStateHandle.get<BookingAppointmentUiModel>(
        BOOKING_APPOINTMENT_UI_MODEL
    )

    fun generateQrCodeBitmap(onGenerated: (Bitmap) -> Unit) {
        viewModelScope.launch {
            val bitmap = bookingAppointmentUiModel.qrCode()
            bitmap?.let { onGenerated.invoke(it) }
        }
    }

    companion object {
        const val BOOKING_APPOINTMENT_UI_MODEL = "bookingAppointmentUiModel"
    }
}
