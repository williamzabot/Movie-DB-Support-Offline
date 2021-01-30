package com.williamzabot.fullmoviedb.features.login.register

import android.text.Editable
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.williamzabot.fullmoviedb.base.BaseViewModel
import com.williamzabot.fullmoviedb.data.db.remotedb.RemoteUserDAO

class RegisterViewModel : BaseViewModel() {

    private val _nameFieldState = MutableLiveData<FieldState>()
    val nameFieldState: LiveData<FieldState> = _nameFieldState

    private val _emailFieldState = MutableLiveData<FieldState>()
    val emailFieldState: LiveData<FieldState> = _emailFieldState

    private val _passwordFieldState = MutableLiveData<FieldState>()
    val passwordFieldState: LiveData<FieldState> = _passwordFieldState

    private val _successCreateUser = MutableLiveData<Boolean>()
    val successCreateUser: LiveData<Boolean> = _successCreateUser

    private val _msgFailCreateUser = MutableLiveData<String>()
    val msgFailCreateUser: LiveData<String> = _msgFailCreateUser

    fun checkFieldsForRegister(
        name: Editable?,
        email: Editable?,
        password: Editable?,
        region: String
    ) {
        if (checkField(name)) {
            _nameFieldState.postValue(FieldState.FieldOk)
        } else {
            _nameFieldState.postValue(FieldState.FieldError)
        }
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

        if (checkField(name) && checkField(email) && checkField(password)) {
            createUser(name.toString(), email.toString(), password.toString(), region)
        }
    }

    private fun createUser(name: String, email: String, password: String, region: String) {
        auth.createUserWithEmailAndPassword(email, password).addOnSuccessListener {
            RemoteUserDAO.saveUser(name, region).let { task ->
                task?.addOnSuccessListener {
                    _successCreateUser.postValue(true)
                }?.addOnFailureListener {
                    _msgFailCreateUser.postValue("Falha ao salvar usuário")
                }
            }
        }.addOnFailureListener {
            answerOptions(it.toString())
        }
    }

    private fun answerOptions(answer: String) {
        when {
            answer.contains("least 6 characters") -> {
                _msgFailCreateUser.postValue("Não é permitido senha menor do que 6 digitos")
            }
            answer.contains("address is badly") -> {
                _msgFailCreateUser.postValue("Email inválido!")
            }
            answer.contains("interrupted connection") -> {
                _msgFailCreateUser.postValue("Sem conexão com a Internet")
            }
            answer.contains("address is already") -> {
                _msgFailCreateUser.postValue("Email já cadastrado")
            }
        }
    }
}