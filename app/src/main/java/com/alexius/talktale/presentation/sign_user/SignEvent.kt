package com.alexius.talktale.presentation.sign_user

sealed class SignEvent {

    object SaveAppEntry: SignEvent()
    data class UpdateEmail(val email: String): SignEvent()
    data class UpdatePassword(val password: String): SignEvent()
}