package com.zapplications.salonbooking.ui.shared

import androidx.lifecycle.ViewModel
import com.zapplications.salonbooking.core.adapter.Item
import com.zapplications.salonbooking.domain.model.SalonUiModel
import com.zapplications.salonbooking.domain.model.ServiceUiModel
import com.zapplications.salonbooking.domain.model.datetime.DateUiModel
import com.zapplications.salonbooking.domain.model.datetime.TimeUiModel
import com.zapplications.salonbooking.ui.stylistlist.adapter.item.StylistViewItem
import com.zapplications.salonbooking.ui.stylistlist.adapter.item.StylistViewType
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AppointmentSharedViewModel @Inject constructor() : ViewModel() {
    var salon: SalonUiModel? = null
    var selectedServices: List<ServiceUiModel>? = null
    var selectedStylist: Item? = null
    var selectedDate: DateUiModel? = null
    var selectedTime: TimeUiModel? = null

    fun getSelectedStylistId() = (selectedStylist as? StylistViewItem)?.stylistUiModel?.id ?: StylistViewType.ANY_STYLIST.name
    fun getSelectedStylistName() = (selectedStylist as? StylistViewItem)?.stylistUiModel?.fullName

    fun clear() {
        salon = null
        selectedServices = null
        selectedStylist = null
        selectedDate = null
        selectedTime = null
    }
}
