package com.alexius.talktale.presentation.profile

import androidx.lifecycle.ViewModel
import com.alexius.core.domain.usecases.profile.SignOutUser
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ProfileScreenViewModel @Inject constructor(
    private val signOutUser: SignOutUser
) : ViewModel() {

    private val auth = Firebase.auth

    val email = auth.currentUser?.email ?: ""

    fun signOut() {
        signOutUser()
    }
}