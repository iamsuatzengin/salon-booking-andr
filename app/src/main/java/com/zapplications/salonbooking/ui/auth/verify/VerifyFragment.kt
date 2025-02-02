package com.zapplications.salonbooking.ui.auth.verify

import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.registerReceiver
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.google.android.gms.auth.api.phone.SmsRetriever
import com.zapplications.salonbooking.R
import com.zapplications.salonbooking.core.viewBinding
import com.zapplications.salonbooking.databinding.FragmentVerifyBinding
import com.zapplications.salonbooking.domain.model.SignInType
import com.zapplications.salonbooking.receiver.SMSReceiver
import com.zapplications.salonbooking.receiver.SmsReceiverListener
import dagger.hilt.android.AndroidEntryPoint
import io.github.jan.supabase.auth.status.SessionStatus
import kotlinx.coroutines.launch

@AndroidEntryPoint
class VerifyFragment : Fragment(R.layout.fragment_verify), SmsReceiverListener {
    private val viewModel: VerifyViewModel by viewModels()
    private val binding by viewBinding(FragmentVerifyBinding::bind)
    private var receiver: SMSReceiver? = null

    private val activityResult =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            val message = result.data?.getStringExtra(SmsRetriever.EXTRA_SMS_MESSAGE)

            parseOtp(message)
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (viewModel.signInType == SignInType.Number) {
            receiver = SMSReceiver()
            startSmsUserConsent()
            registerSmsReceiver()
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initOtpView()
        collectData()

        binding.tvSentDigitCodeInfo.text = getString(
            R.string.text_sent_digit_code_info,
            viewModel.emailOrPhoneNumber
        )

        viewModel.sendOTP()

        binding.tvResendMessage.setOnClickListener {
          viewModel.resendOTP()
        }
    }

    private fun collectData() {
        lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(state = Lifecycle.State.STARTED) {
                viewModel.authenticationState.collect { state ->
                    if (state is SessionStatus.Authenticated) {
                        findNavController().navigate(R.id.verifyToHome)
                    }
                }
            }
        }
    }

    private fun initOtpView() = with(binding) {
        btnContinue.setOnClickListener {
            if (layoutOtp.hasAnyEmptyField()) {
                layoutOtp.onError()
                tvOtpErrorText.isVisible = true
                tvOtpErrorText.text = getString(R.string.text_otp_empty_message)
                return@setOnClickListener
            }

            if (viewModel.signInType == SignInType.Number && layoutOtp.getOtpText() != viewModel.otpCode) {
                layoutOtp.onError()
                tvOtpErrorText.isVisible = true
                tvOtpErrorText.text = getString(R.string.text_otp_error_message)
                return@setOnClickListener
            }
            tvOtpErrorText.isVisible = false

            viewModel.verifyOTP(viewModel.otpCode ?: layoutOtp.getOtpText())
        }
    }

    private fun startSmsUserConsent() {
        SmsRetriever.getClient(requireContext()).startSmsUserConsent(/* senderPhoneNumber */ null)
    }

    private fun registerSmsReceiver() {
        receiver?.let { smsReceiver ->
            val intentFilter = IntentFilter(SmsRetriever.SMS_RETRIEVED_ACTION)
            smsReceiver.smsReceiverListener = this
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                requireActivity().registerReceiver(
                    smsReceiver,
                    intentFilter,
                    SmsRetriever.SEND_PERMISSION,
                    null,
                    Context.RECEIVER_EXPORTED,
                )
            } else {
                registerReceiver(
                    requireActivity(),
                    smsReceiver,
                    intentFilter,
                    SmsRetriever.SEND_PERMISSION,
                    null,
                    ContextCompat.RECEIVER_EXPORTED,
                )
            }
        }
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
                viewModel.otpCode = code
                binding.layoutOtp.setOtpText(code)
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        receiver?.let { requireActivity().unregisterReceiver(it) }
        receiver = null
    }

    companion object {
        const val OTP_PATTERN = "(\\d{6})"
    }
}
