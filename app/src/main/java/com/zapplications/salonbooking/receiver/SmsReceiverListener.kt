package com.zapplications.salonbooking.receiver

import android.content.Intent

interface SmsReceiverListener {
    fun onReceived(intent: Intent?)
    fun onTimeout()
    fun onFailure(it: Throwable)
}
