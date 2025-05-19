package com.example.invengo.auth

data class SignInResult(
    val data: Unit?,
    val errorMessage: String?
)
data class UserData(
    val userId: String,
    val username: String?,
    val email: String?,
    val ProfilePictureUrl: String?,
    val data: UserData?
)


