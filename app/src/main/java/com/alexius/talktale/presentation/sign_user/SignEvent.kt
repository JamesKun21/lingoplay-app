package com.alexius.talktale.presentation.sign_user

sealed class SignEvent {

    object SaveAppEntry: SignEvent()
    data class UpdateFullName(val fullName: String): SignEvent()
    data class UpdateBirthDate(val birthDate: String): SignEvent()
    data class UpdateEmail(val email: String): SignEvent()
    data class UpdatePassword(val password: String): SignEvent()
    data class UpdatePhoneNumber(val phoneNumber: String): SignEvent()
    object SignInWIthGoogle: SignEvent()
    object SignInWithEmail: SignEvent()
    object SignUpWithEmail: SignEvent()
}