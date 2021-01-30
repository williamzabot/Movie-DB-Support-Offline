package com.williamzabot.fullmoviedb.features.login

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.google.android.material.textfield.TextInputEditText
import com.williamzabot.fullmoviedb.R
import com.williamzabot.fullmoviedb.base.BaseViewModel
import com.williamzabot.fullmoviedb.extensions.toast
import com.williamzabot.fullmoviedb.features.home.HomeActivity

class LoginFragment : Fragment(R.layout.fragment_login) {

    private lateinit var buttonLogin: Button
    private lateinit var txtReset: TextView
    private lateinit var txtRegister: TextView
    private lateinit var loginEmail: TextInputEditText
    private lateinit var loginPass: TextInputEditText
    private val navController by lazy { findNavController() }
    private val viewModel by viewModels<LoginViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init(view)
        observeEvents()
        clickEvents()
    }

    private fun observeEvents() {
        viewModel.emailFieldState.observe(viewLifecycleOwner) { emailState ->
            when (emailState) {
                is BaseViewModel.FieldState.FieldOk -> {
                    loginEmail.error = null
                }
                is BaseViewModel.FieldState.FieldError -> {
                    loginEmail.error = "Email é obrigatório"
                }
            }
        }

        viewModel.passwordFieldState.observe(viewLifecycleOwner) { passwordState ->
            when (passwordState) {
                is BaseViewModel.FieldState.FieldOk -> {
                    loginPass.error = null
                }
                is BaseViewModel.FieldState.FieldError -> {
                    loginPass.error = "Senha é obrigatória"
                }
            }
        }

        viewModel.successLogin.observe(viewLifecycleOwner) {
            val intent = Intent(requireActivity(), HomeActivity::class.java)
            startActivity(intent)
            activity?.finish()
        }

        viewModel.failLogin.observe(viewLifecycleOwner) {
            toast("Email e senha não correspondem!")
        }

    }

    private fun init(view: View) {
        buttonLogin = view.findViewById(R.id.button_login)
        loginEmail = view.findViewById(R.id.login_email)
        loginPass = view.findViewById(R.id.login_password)
        txtRegister = view.findViewById(R.id.txt_register)
        txtReset = view.findViewById(R.id.txt_reset)
    }

    private fun clickEvents() {
        clickButtonLogin()
        clickButtonRegister()
        clickButtonReset()
    }

    private fun clickButtonReset() {
        txtReset.setOnClickListener {
            navController.navigate(R.id.action_login_to_resetpass)
        }
    }

    private fun clickButtonRegister() {
        txtRegister.setOnClickListener {
            navController.navigate(R.id.action_login_to_register)
        }
    }

    private fun clickButtonLogin() {
        buttonLogin.setOnClickListener {
            viewModel.checkEmailAndPasswordForLogin(loginEmail.text, loginPass.text)
        }
    }


}