package com.example.invengo.auth

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import androidx.lifecycle.ViewModel

class SignInViewModel(): ViewModel() {
    private val _state = MutableStateFlow(SignInState())
    val state = _state.asStateFlow()

    fun onSignedInResult(result:SignInResult){
        _state.update { it.copy(
            isSignInSuccessful = result.data != null,
            isSignInError = result.errorMessage
        ) }
    }
    fun resetState() {
        _state.update { SignInState() }
    }

}


