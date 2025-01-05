package com.alexius.talktale.presentation.sign_user

data class SignUpState(
    val fullName: String = "",
    val email: String = "",
    val password: String = "",
    val birthDate: String = "",
    val phoneNumber: String = "",
)