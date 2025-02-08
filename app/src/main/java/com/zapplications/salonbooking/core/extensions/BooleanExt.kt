package com.zapplications.salonbooking.core.extensions

fun Boolean?.orTrue() = this ?: true
fun Boolean?.orFalse() = this ?: false
