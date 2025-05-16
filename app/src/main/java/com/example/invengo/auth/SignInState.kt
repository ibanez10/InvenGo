package com.example.invengo.auth

data class SignInState(
    val isSignInSuccessful: Boolean = false,
    val isSignInError: String? = null
)
