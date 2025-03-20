package com.zapplications.salonbooking.core.extensions

const val ZERO = 0
const val ONE = 1
const val TWO = 2
const val TEN = 10

const val CORNER_RADIUS_DEFAULT_PX = 10

fun Int?.orZero() = this ?: 0
