package com.zapplications.salonbooking.ui.auth.verify

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import com.zapplications.salonbooking.R
import com.zapplications.salonbooking.core.viewBinding
import com.zapplications.salonbooking.databinding.FragmentVerifyBinding

class VerifyFragment : Fragment(R.layout.fragment_verify) {
    private val binding by viewBinding(FragmentVerifyBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initOtpView()
    }

    private fun initOtpView() = with(binding) {

        btnContinue.setOnClickListener {
            if (layoutOtp.hasAnyEmptyField()) {
                layoutOtp.errorState()
                Toast.makeText(requireContext(), "There are empty fields", Toast.LENGTH_SHORT).show()
                tvOtpErrorText.isVisible = true
                tvOtpErrorText.text = getString(R.string.text_otp_empty_message)
                return@setOnClickListener
            }

            if (layoutOtp.getOtpText() != "1234") {
                layoutOtp.errorState()
                Toast.makeText(requireContext(), "Wrong code!", Toast.LENGTH_SHORT).show()
                tvOtpErrorText.isVisible = true
                tvOtpErrorText.text = getString(R.string.text_otp_error_message)
                return@setOnClickListener
            }
            tvOtpErrorText.isVisible = false
            Toast.makeText(requireContext(), layoutOtp.getOtpText(), Toast.LENGTH_SHORT).show()
        }
    }
}