package com.zapplications.salonbooking.ui.bookings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zapplications.salonbooking.data.client.supabaseClient
import com.zapplications.salonbooking.domain.model.BookingsUiModel
import com.zapplications.salonbooking.domain.model.enums.BookingStatusType
import com.zapplications.salonbooking.domain.repository.BookingRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import io.github.jan.supabase.auth.auth
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BookingsViewModel @Inject constructor(
    private val bookingRepository: BookingRepository,
) : ViewModel() {
    private val _uiState = MutableStateFlow<List<BookingsUiModel>>(emptyList())
    val uiState get() = _uiState.asStateFlow()

    private val _uiEvent = MutableSharedFlow<BookingsUiEvent>()
    val uiEvent get() = _uiEvent.asSharedFlow()

    fun getUserBookings(status: String?) {
        viewModelScope.launch {
            val userId = supabaseClient.auth.currentSessionOrNull()?.user?.id
            if (userId.isNullOrEmpty() || status.isNullOrEmpty()) return@launch

            val result = bookingRepository.getUserBookings(
                userId = userId,
                status = status
            )

            _uiState.update { result }
        }
    }

    fun getBookingById(bookingId: String) {
        viewModelScope.launch {
            val result = bookingRepository.getBookById(bookingId)

            result?.let {
                _uiEvent.emit(BookingsUiEvent.NavigateToReceipt(it))
            } ?: _uiEvent.emit(BookingsUiEvent.ShowError)
        }
    }

    fun cancelBooking(bookingId: String) {
        viewModelScope.launch {
            bookingRepository.updateBooking(
                bookingId = bookingId,
                statusType = BookingStatusType.CANCELLED
            )?.let {
                getUserBookings(status = BookingStatusType.UPCOMING.name)
                _uiEvent.emit(BookingsUiEvent.BookingCancelledSuccess)
            } ?: _uiEvent.emit(BookingsUiEvent.ShowError)
        }
    }
}
