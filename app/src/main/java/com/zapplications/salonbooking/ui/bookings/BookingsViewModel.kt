package com.zapplications.salonbooking.ui.bookings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zapplications.salonbooking.data.client.supabaseClient
import com.zapplications.salonbooking.domain.model.BookingsUiModel
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
    private val bookingsRemoteDataSource: BookingRepository,
) : ViewModel() {
    private val _uiState = MutableStateFlow<List<BookingsUiModel>>(emptyList())
    val uiState get() = _uiState.asStateFlow()

    fun getUserBookings(status: String?) {
        viewModelScope.launch {
            val userId = supabaseClient.auth.currentSessionOrNull()?.user?.id
            if (userId.isNullOrEmpty() || status.isNullOrEmpty()) return@launch

            val result = bookingsRemoteDataSource.getUserBookings(
                userId = userId,
                status = status
            )

            _uiState.update { result }
        }
    }
}
