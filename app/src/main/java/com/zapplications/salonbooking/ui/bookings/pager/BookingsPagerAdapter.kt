package com.zapplications.salonbooking.ui.bookings.pager

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.zapplications.salonbooking.domain.model.enums.BookingStatusType

class BookingsPagerAdapter(fragment: Fragment): FragmentStateAdapter(fragment) {
    override fun getItemCount(): Int = BookingStatusType.entries.size

    override fun createFragment(position: Int): Fragment {
        val bookingStatus = BookingStatusType.entries[position]
        return BookingsPagerItemFragment.newInstance(bookingStatus)
    }
}
