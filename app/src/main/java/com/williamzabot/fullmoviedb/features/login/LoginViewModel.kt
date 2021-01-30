package com.williamzabot.fullmoviedb.features.login

import android.text.Editable
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.williamzabot.fullmoviedb.base.BaseViewModel

class LoginViewModel : BaseViewModel() {

    private val _emailFieldState = MutableLiveData<FieldState>()
    val emailFieldState: LiveData<FieldState> = _emailFieldState

    private val _passwordFieldState = MutableLiveData<FieldState>()
    val passwordFieldState: LiveData<FieldState> = _passwordFieldState

    private val _successLogin = MutableLiveData<Boolean>()
    val successLogin: LiveData<Boolean> = _successLogin

    private val _failLogin = MutableLiveData<Boolean>()
    val failLogin: LiveData<Boolean> = _failLogin

    fun checkEmailAndPasswordForLogin(email: Editable?, password: Editable?) {
        if (checkField(email)) {
            _emailFieldState.postValue(FieldState.FieldOk)
        } else {
            _emailFieldState.postValue(FieldState.FieldError)
        }
        if (checkField(password)) {
            _passwordFieldState.postValue(FieldState.FieldOk)
        } else {
            _passwordFieldState.postValue(FieldState.FieldError)
        }

        if (checkField(email) && checkField(password)) {
            login(email.toString(), password.toString())
        }
    }

    private fun login(email: String, senha: String) {
        auth.signInWithEmailAndPassword(email, senha)
            .addOnSuccessListener {
                _successLogin.postValue(true)

            }.addOnFailureListener {
                _failLogin.postValue(true)
            }
    }
}