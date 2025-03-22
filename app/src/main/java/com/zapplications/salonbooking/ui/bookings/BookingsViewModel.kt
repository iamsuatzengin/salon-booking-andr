package com.zapplications.salonbooking.ui.bookings

import androidx.lifecycle.viewModelScope
import com.zapplications.salonbooking.core.ui.BaseViewModel
import com.zapplications.salonbooking.core.ui.ShowError
import com.zapplications.salonbooking.data.client.supabaseClient
import com.zapplications.salonbooking.domain.model.BookingsUiModel
import com.zapplications.salonbooking.domain.model.enums.BookingStatusType
import com.zapplications.salonbooking.domain.repository.BookingRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import io.github.jan.supabase.auth.auth
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BookingsViewModel @Inject constructor(
    private val bookingRepository: BookingRepository,
) : BaseViewModel() {
    private val _uiState = MutableStateFlow<List<BookingsUiModel>>(emptyList())
    val uiState get() = _uiState.asStateFlow()

    fun getUserBookings(status: String?) {
        viewModelScope.launch {
            val userId = supabaseClient.auth.currentSessionOrNull()?.user?.id
            if (userId.isNullOrEmpty() || status.isNullOrEmpty()) return@launch

            call(
                block = {
                    bookingRepository.getUserBookings(
                        userId = userId,
                        status = status
                    )
                },
                onSuccess = { list ->
                    _uiState.update {
                        list
                    }
                }
            )
        }
    }

    fun getBookingById(bookingId: String) {
        call(
            block = {
                bookingRepository.getBookById(bookingId)
            },
            onSuccess = { bookingAppointment ->
                bookingAppointment?.let {
                    sendEvent(NavigateToReceipt(it))
                } ?: sendEvent(ShowError("Booking not found"))
            }
        )
    }

    fun cancelBooking(bookingId: String) {
        call(
            block = {
                bookingRepository.updateBooking(
                    bookingId = bookingId,
                    statusType = BookingStatusType.CANCELLED
                )
            },
            onSuccess = { result ->
                result?.let {
                    getUserBookings(status = BookingStatusType.UPCOMING.name)
                    sendEvent(BookingCancelledSuccess)
                } ?: sendEvent(ShowError("Booking not cancelled!"))
            }
        )
    }
}
