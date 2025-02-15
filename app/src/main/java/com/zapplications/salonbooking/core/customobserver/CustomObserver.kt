package com.zapplications.salonbooking.core.customobserver

fun interface CustomObserver<T> {
    fun notify(notifiedItem: T)
}
