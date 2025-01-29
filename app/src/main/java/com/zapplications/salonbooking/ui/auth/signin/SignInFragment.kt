package com.zapplications.salonbooking.ui.auth.signin

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.annotation.DrawableRes
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.zapplications.salonbooking.R
import com.zapplications.salonbooking.core.extensions.drawable
import com.zapplications.salonbooking.core.viewBinding
import com.zapplications.salonbooking.databinding.FragmentSignInBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SignInFragment : Fragment(R.layout.fragment_sign_in) {
    private val binding by viewBinding(FragmentSignInBinding::bind)
    private val viewModel: SignInViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initView()
        collectData()
    }

    private fun collectData() {
        lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(state = Lifecycle.State.STARTED) {
                viewModel.uiEvent.collect { uiEvent ->
                    when (uiEvent) {
                        is SignInUiEvent.NavigateToVerifyScreen -> {
                            changeEditTextBackground(R.drawable.bg_edittext_white_rounded)
                            navigateToVerifyScreen(uiEvent)
                        }

                        is SignInUiEvent.ShowError -> {
                            changeEditTextBackground(R.drawable.bg_edittext_error_state)
                        }
                    }
                }
            }
        }
    }

    private fun navigateToVerifyScreen(uiEvent: SignInUiEvent.NavigateToVerifyScreen) {
        val action = SignInFragmentDirections.signInFragmentToVerifyFragment(
            uiEvent.input,
            uiEvent.signInType
        )
        findNavController().navigate(action)
    }

    private fun initView() = with(binding) {
        btnContinue.setOnClickListener {
            val emailOrPhoneNumber = etEmailOrPhoneNumber.text.toString().trim()
            viewModel.handleContinue(input = emailOrPhoneNumber)
        }

        binding.btnContinueWithApple.setOnClickListener {
            Toast.makeText(requireContext(), "Apple clicked", Toast.LENGTH_SHORT).show()
        }

        binding.btnContinueWithGoogle.setOnClickListener {
            Toast.makeText(requireContext(), "Google clicked", Toast.LENGTH_SHORT).show()
        }
    }

    private fun changeEditTextBackground(@DrawableRes drawable: Int) {
        binding.etEmailOrPhoneNumber.background = resources.drawable(drawable)
    }
}
