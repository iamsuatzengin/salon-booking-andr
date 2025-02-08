package com.zapplications.salonbooking.core.extensions

const val ZERO = 0
const val ONE = 1

fun Int?.orZero() = this ?: 0
