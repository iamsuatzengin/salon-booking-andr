package com.zapplications.salonbooking.ui.auth.verify

import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.registerReceiver
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import com.google.android.gms.auth.api.phone.SmsRetriever
import com.zapplications.salonbooking.R
import com.zapplications.salonbooking.core.viewBinding
import com.zapplications.salonbooking.databinding.FragmentVerifyBinding
import com.zapplications.salonbooking.receiver.SMSReceiver
import com.zapplications.salonbooking.receiver.SmsReceiverListener
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class VerifyFragment : Fragment(R.layout.fragment_verify), SmsReceiverListener {
    private val binding by viewBinding(FragmentVerifyBinding::bind)

    private var receiver: SMSReceiver? = null

    private val activityResult =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            val message = result.data?.getStringExtra(SmsRetriever.EXTRA_SMS_MESSAGE)

            parseOtp(message)
        }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initOtpView()
        receiver = SMSReceiver()
        startSmsUserConsent()
        registerSmsReceiver()
    }

    private fun initOtpView() = with(binding) {
        btnContinue.setOnClickListener {
            if (layoutOtp.hasAnyEmptyField()) {
                layoutOtp.onError()
                Toast.makeText(requireContext(), "There are empty fields", Toast.LENGTH_SHORT)
                    .show()
                tvOtpErrorText.isVisible = true
                tvOtpErrorText.text = getString(R.string.text_otp_empty_message)
                return@setOnClickListener
            }

            if (layoutOtp.getOtpText() != "1234") {
                layoutOtp.onError()
                Toast.makeText(requireContext(), "Wrong code!", Toast.LENGTH_SHORT).show()
                tvOtpErrorText.isVisible = true
                tvOtpErrorText.text = getString(R.string.text_otp_error_message)
                return@setOnClickListener
            }
            tvOtpErrorText.isVisible = false
            Toast.makeText(requireContext(), layoutOtp.getOtpText(), Toast.LENGTH_SHORT).show()
        }
    }

    private fun startSmsUserConsent() {
        SmsRetriever.getClient(requireContext()).startSmsUserConsent(/* senderPhoneNumber */ null)
    }

    private fun registerSmsReceiver() {
        val intentFilter = IntentFilter(SmsRetriever.SMS_RETRIEVED_ACTION)
        receiver?.smsReceiverListener = this
        registerReceiver(
            requireContext(),
            receiver,
            intentFilter,
            SmsRetriever.SEND_PERMISSION,
            null,
            ContextCompat.RECEIVER_EXPORTED,
        )
    }

    override fun onReceived(intent: Intent?) {
        intent?.let { activityResult.launch(it) }
    }

    override fun onTimeout() {
        Log.e("VerifyFragment", "Timeout")
    }

    override fun onFailure(it: Throwable) {
        Log.e("VerifyFragment", "onFailure: $it", it)
    }

    private fun parseOtp(message: String?) {
        message?.let { sms ->
            val regex = Regex(OTP_PATTERN)
            if (regex.containsMatchIn(sms)) {
                val code = regex.find(sms)?.value
                binding.layoutOtp.setOtpText(code)
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        requireActivity().unregisterReceiver(receiver)
    }

    companion object {
        const val OTP_PATTERN = "(\\d{4})"
    }
}
