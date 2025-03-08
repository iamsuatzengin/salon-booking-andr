package com.zapplications.salonbooking.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
@Parcelize
data class SelectedServices(
    @SerialName("service_name") val serviceName: String,
    @SerialName("service_price") val servicePrice: Double,
) : Parcelable
