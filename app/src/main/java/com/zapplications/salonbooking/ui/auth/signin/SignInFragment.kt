package com.zapplications.salonbooking.ui.auth.signin

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.annotation.DrawableRes
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.zapplications.salonbooking.R
import com.zapplications.salonbooking.core.extensions.drawable
import com.zapplications.salonbooking.core.ui.BaseFragment
import com.zapplications.salonbooking.core.ui.applyinset.InsetSides
import com.zapplications.salonbooking.core.ui.applyinset.applySystemBarInsetsAsPadding
import com.zapplications.salonbooking.core.viewBinding
import com.zapplications.salonbooking.databinding.FragmentSignInBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SignInFragment : BaseFragment<SignInViewModel>(R.layout.fragment_sign_in) {
    private val binding by viewBinding(FragmentSignInBinding::bind)
    override val viewModel: SignInViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initView()
    }

    override suspend fun collectUiEvents() {
        super.collectUiEvents()
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

    private fun navigateToVerifyScreen(uiEvent: SignInUiEvent.NavigateToVerifyScreen) {
        val action = SignInFragmentDirections.signInFragmentToVerifyFragment(
            uiEvent.input,
            uiEvent.signInType
        )
        findNavController().navigate(action)
    }

    private fun initView() = with(binding) {
        binding.llContent.applySystemBarInsetsAsPadding(InsetSides(bottom = true, isConsumed = true))
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
