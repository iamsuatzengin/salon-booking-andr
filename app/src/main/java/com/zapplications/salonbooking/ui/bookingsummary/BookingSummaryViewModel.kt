package com.zapplications.salonbooking.ui.bookingsummary

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zapplications.salonbooking.data.client.supabaseClient
import com.zapplications.salonbooking.data.request.BookingAppointmentRequest
import com.zapplications.salonbooking.domain.model.SelectedServices
import com.zapplications.salonbooking.domain.model.enums.BookingStatusType
import com.zapplications.salonbooking.domain.model.enums.PaymentType
import com.zapplications.salonbooking.domain.repository.BookingRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import io.github.jan.supabase.auth.auth
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BookingSummaryViewModel @Inject constructor(
    private val bookingRepository: BookingRepository,
) : ViewModel() {
    private val _uiState = MutableStateFlow(BookingSummaryUiState())
    val uiState: StateFlow<BookingSummaryUiState> get() = _uiState.asStateFlow()

    private val _uiEvent = MutableSharedFlow<BookingSummaryUiEvent>()
    val uiEvent: SharedFlow<BookingSummaryUiEvent> get() = _uiEvent.asSharedFlow()

    fun bookAppointment(bookingRequest: BookingAppointmentRequest?) {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }

            val response = bookingRequest?.let { bookingRepository.bookAppointment(it) }
            response?.let { responseModel ->
                _uiState.update { it.copy(isLoading = false) }
                _uiEvent.emit(BookingSummaryUiEvent.BookingAppointmentSuccessFull(responseModel))
            } ?: _uiEvent.emit(BookingSummaryUiEvent.ShowError("Something went wrong!"))
        }
    }

    fun createRequest(
        salonId: String,
        stylistId: String,
        bookingDate: String,
        bookingTime: String,
        totalAmount: Double,
        discountAmount: Int,
        finalAmount: Double,
        paymentType: PaymentType,
        selectedServices: List<SelectedServices>,
    ): BookingAppointmentRequest? {
        val statusType = BookingStatusType.UPCOMING
        val customerId = supabaseClient.auth.currentUserOrNull()?.id ?: return null

        return  BookingAppointmentRequest(
            salonId = salonId,
            customerId = customerId,
            stylistId = stylistId,
            bookingDate = bookingDate,
            bookingTime = bookingTime,
            totalAmount = totalAmount,
            discountAmount = discountAmount,
            finalAmount = finalAmount,
            paymentType = paymentType.name,
            status = statusType.name,
            selectedServices = selectedServices
        )
    }
}
