package com.zapplications.salonbooking.ui.bookings

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.google.android.material.tabs.TabLayoutMediator
import com.zapplications.salonbooking.R
import com.zapplications.salonbooking.core.ui.applyinset.InsetSides
import com.zapplications.salonbooking.core.ui.applyinset.applySystemBarInsetsAsPadding
import com.zapplications.salonbooking.core.viewBinding
import com.zapplications.salonbooking.databinding.FragmentBookingsBinding
import com.zapplications.salonbooking.domain.model.enums.BookingStatusType
import com.zapplications.salonbooking.ui.bookings.pager.BookingsPagerAdapter
import dagger.hilt.android.AndroidEntryPoint

/**
 * TODO [com.zapplications.salonbooking.core.ui.BaseFragment] implement et
 */
@AndroidEntryPoint
class BookingsFragment : Fragment(R.layout.fragment_bookings) {
    private val binding by viewBinding(FragmentBookingsBinding::bind)

    private var pagerAdapter: BookingsPagerAdapter? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        pagerAdapter = BookingsPagerAdapter(this)
        initView()
    }

    private fun initView() = with(binding) {
        root.applySystemBarInsetsAsPadding(InsetSides(top = true))
        viewPager.adapter = pagerAdapter
        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            tab.text = BookingStatusType.toResString(
                context = requireContext(),
                statusType = BookingStatusType.entries.getOrNull(position)
            )
        }.attach()
    }
}
