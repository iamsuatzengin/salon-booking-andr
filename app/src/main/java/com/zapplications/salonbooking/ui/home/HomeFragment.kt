package com.zapplications.salonbooking.ui.home

import androidx.fragment.app.Fragment
import com.zapplications.salonbooking.R
import com.zapplications.salonbooking.core.viewBinding
import com.zapplications.salonbooking.databinding.FragmentHomeBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment(R.layout.fragment_home) {
    val binding by viewBinding(FragmentHomeBinding::bind)
}
