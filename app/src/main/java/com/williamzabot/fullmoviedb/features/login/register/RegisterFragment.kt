package com.williamzabot.fullmoviedb.features.login.register

import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Spinner
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.google.android.material.textfield.TextInputEditText
import com.williamzabot.fullmoviedb.R
import com.williamzabot.fullmoviedb.base.BaseViewModel
import com.williamzabot.fullmoviedb.extensions.toast

class RegisterFragment : Fragment(R.layout.fragment_register) {

    private lateinit var spinnerRegion: Spinner
    private lateinit var buttonRegister: Button
    private lateinit var registerName: TextInputEditText
    private lateinit var registerEmail: TextInputEditText
    private lateinit var registerPassword: TextInputEditText
    private val viewModel by viewModels<RegisterViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init(view)
        observeEvents()
        configSpinner()
        clickButtonRegister()
    }

    private fun observeEvents() {
        viewModel.nameFieldState.observe(viewLifecycleOwner) { nameState ->
            when (nameState) {
                is BaseViewModel.FieldState.FieldOk -> {
                    registerName.error = null
                }
                is BaseViewModel.FieldState.FieldError -> {
                    registerName.error = "Nome é obrigatório"
                }
            }
        }

        viewModel.emailFieldState.observe(viewLifecycleOwner) { emailState ->
            when (emailState) {
                is BaseViewModel.FieldState.FieldOk -> {
                    registerEmail.error = null
                }
                is BaseViewModel.FieldState.FieldError -> {
                    registerEmail.error = "Email é obrigatório"
                }
            }
        }

        viewModel.passwordFieldState.observe(viewLifecycleOwner) { passwordState ->
            when (passwordState) {
                is BaseViewModel.FieldState.FieldOk -> {
                    registerPassword.error = null
                }
                is BaseViewModel.FieldState.FieldError -> {
                    registerPassword.error = "Senha é obrigatória"
                }
            }
        }

        viewModel.successCreateUser.observe(viewLifecycleOwner) {
            toast("Sucesso ao criar usuário!")
            clearScreen()
        }

        viewModel.msgFailCreateUser.observe(viewLifecycleOwner) { msgError ->
            toast(msgError)

        }
    }

    private fun init(view: View) {
        spinnerRegion = view.findViewById(R.id.spinner_region)
        buttonRegister = view.findViewById(R.id.button_register)
        registerName = view.findViewById(R.id.register_name)
        registerEmail = view.findViewById(R.id.register_email)
        registerPassword = view.findViewById(R.id.register_password)
    }

    private fun configSpinner() {
        val adapter = ArrayAdapter.createFromResource(
            requireContext(), R.array.regions, android.R.layout.simple_spinner_dropdown_item
        )
        spinnerRegion.adapter = adapter
    }

    private fun clickButtonRegister() {
        buttonRegister.setOnClickListener {
            viewModel.checkFieldsForRegister(
                registerName.text,
                registerEmail.text,
                registerPassword.text,
                spinnerRegion.selectedItem.toString()
            )
        }
    }

    private fun clearScreen() {
        registerEmail.text?.clear()
        registerName.text?.clear()
        registerPassword.text?.clear()
    }


}