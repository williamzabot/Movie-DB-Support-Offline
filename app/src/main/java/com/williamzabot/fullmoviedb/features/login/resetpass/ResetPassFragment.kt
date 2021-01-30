package com.williamzabot.fullmoviedb.features.login.resetpass

import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.google.android.material.textfield.TextInputEditText
import com.williamzabot.fullmoviedb.R
import com.williamzabot.fullmoviedb.base.BaseViewModel
import com.williamzabot.fullmoviedb.extensions.toast

class ResetPassFragment : Fragment(R.layout.fragment_reset_pass) {

    private lateinit var buttonReset: Button
    private lateinit var emailReset: TextInputEditText
    private val viewModel by viewModels<ResetPassViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init(view)
        observeEvents()
        clickButtonReset()
    }

    private fun observeEvents() {
        viewModel.emailFieldState.observe(viewLifecycleOwner) { emailState ->
            when (emailState) {
                is BaseViewModel.FieldState.FieldOk -> {
                    emailReset.error = null
                }
                is BaseViewModel.FieldState.FieldError -> {
                    emailReset.error = "Email é obrigatório"
                }
            }
        }

        viewModel.successResetPass.observe(viewLifecycleOwner) {
            toast("Um email foi encaminhado para você resetar sua senha!")
            emailReset.text?.clear()
        }

        viewModel.errorReset.observe(viewLifecycleOwner) {
            toast("Email não encontrado!")
        }
    }

    private fun init(view: View) {
        buttonReset = view.findViewById(R.id.button_reset)
        emailReset = view.findViewById(R.id.email_reset)
    }

    private fun clickButtonReset() {
        buttonReset.setOnClickListener {
            viewModel.checkEmailForReset(emailReset.text)
        }
    }


}