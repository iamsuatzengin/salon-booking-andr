package com.zapplications.salonbooking.ui.auth.signin

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.zapplications.salonbooking.R
import com.zapplications.salonbooking.core.extensions.drawable
import com.zapplications.salonbooking.core.viewBinding
import com.zapplications.salonbooking.databinding.FragmentSignInBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SignInFragment : Fragment(R.layout.fragment_sign_in) {
    private val binding by viewBinding(FragmentSignInBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        binding.btnContinueWithApple.setOnClickListener {
            // Handle continue button click
            Toast.makeText(requireContext(), "Apple clicked", Toast.LENGTH_SHORT).show()
        }

        binding.btnContinueWithGoogle.setOnClickListener {
            // Handle continue button click
            Toast.makeText(requireContext(), "Google clicked", Toast.LENGTH_SHORT).show()
        }
    }

    private fun initView() = with(binding) {
        btnContinue.setOnClickListener {
            val emailOrPhoneNumber = etEmailOrPhoneNumber.text.toString().trim()
            if (emailOrPhoneNumber.isEmpty()) {
                etEmailOrPhoneNumber.background = resources.drawable(R.drawable.bg_edittext_error_state)
            } else {
                etEmailOrPhoneNumber.background = resources.drawable(R.drawable.bg_edittext_white_rounded)
                findNavController().navigate(R.id.signInFragmentToVerifyFragment)
            }
        }
    }
}