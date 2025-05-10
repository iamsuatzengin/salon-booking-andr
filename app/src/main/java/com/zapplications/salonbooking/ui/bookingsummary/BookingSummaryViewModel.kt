package com.zapplications.salonbooking.ui.bookingsummary

import com.zapplications.salonbooking.core.ui.BaseViewModel
import com.zapplications.salonbooking.data.client.supabaseClient
import com.zapplications.salonbooking.data.request.BookingAppointmentRequest
import com.zapplications.salonbooking.domain.model.SelectedServices
import com.zapplications.salonbooking.domain.model.enums.BookingStatusType
import com.zapplications.salonbooking.domain.model.enums.PaymentType
import com.zapplications.salonbooking.domain.repository.BookingRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import io.github.jan.supabase.auth.auth
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class BookingSummaryViewModel @Inject constructor(
    private val bookingRepository: BookingRepository,
) : BaseViewModel() {
    private val _uiState = MutableStateFlow(BookingSummaryUiState())
    val uiState: StateFlow<BookingSummaryUiState> get() = _uiState.asStateFlow()

    fun bookAppointment(bookingRequest: BookingAppointmentRequest?) {
        call(
            block = {
                val uiModel = bookingRequest?.let { bookingRepository.bookAppointment(it) }
                delay(2000)

                uiModel // return booking appointment ui model
            },
            onSuccess = { uiModel ->
                uiModel?.let {
                    sendEvent(BookingSummaryUiEvent.BookingAppointmentSuccessFull(it))
                }
            }
        )
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
