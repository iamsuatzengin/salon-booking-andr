package com.zapplications.salonbooking.core.extensions

const val ZERO = 0
const val ONE = 1
const val TWO = 2
const val TEN = 10

fun Int?.orZero() = this ?: 0
