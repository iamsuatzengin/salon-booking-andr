package com.zapplications.salonbooking.data.client

object SupabaseConstants {
    const val FUNC_GET_ALL_HOME_PAGE_DATA = "get_all_homepage_data"
    const val FUNC_GET_HOME_PAGE_DATA_BY_LOCATION = "get_homepage_data_by_location"
    const val FUNC_GET_SALON_DETAILS = "get_salon_details"
    const val FUNC_GET_STYLIST_AVAILABILITY = "get_stylist_availability"
    const val FUNC_INSERT_AND_GET_BOOKINGS = "insert_and_get_booking"

    // Table names
    const val TABLE_STYLIST = "stylist"
    const val TABLE_BOOKINGS = "bookings"

    // Column names
    const val FUNC_PARAM_SALON_ID = "p_salon_id"
    const val FILTER_SALON_ID = "salon_id"
}
