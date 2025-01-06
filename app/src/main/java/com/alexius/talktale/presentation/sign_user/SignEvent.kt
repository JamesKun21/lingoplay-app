package com.alexius.talktale.presentation.sign_user

sealed class SignEvent {

    object SaveAppEntry: SignEvent()
    data class UpdateFullName(val fullName: String): SignEvent()
    data class UpdateBirthDate(val birthDate: String): SignEvent()
    data class UpdateEmail(val email: String): SignEvent()
    data class UpdatePassword(val password: String): SignEvent()
    data class UpdateEmailSignUp(val email: String): SignEvent()
    data class UpdatePasswordSignUp(val password: String): SignEvent()
    data class UpdatePhoneNumber(val phoneNumber: String): SignEvent()
    data class SignInWIthGoogle(val callback: () -> Unit): SignEvent()
    data class SignInWithEmail(val callback: () -> Unit): SignEvent()
    data class SignUpWithEmail(val callback: () -> Unit): SignEvent()
}