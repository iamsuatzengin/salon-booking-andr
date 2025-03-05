package com.zapplications.salonbooking.ui.shared

import androidx.lifecycle.ViewModel
import com.zapplications.salonbooking.domain.model.SalonUiModel
import com.zapplications.salonbooking.domain.model.ServiceUiModel
import com.zapplications.salonbooking.domain.model.StylistUiModel
import com.zapplications.salonbooking.domain.model.datetime.DateUiModel
import com.zapplications.salonbooking.domain.model.datetime.TimeUiModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AppointmentSharedViewModel @Inject constructor() : ViewModel() {
    var salon: SalonUiModel? = null
    var selectedServices: List<ServiceUiModel>? = null
    var selectedStylist: StylistUiModel? = null
    var selectedDate: DateUiModel? = null
    var selectedTime: TimeUiModel? = null
}
