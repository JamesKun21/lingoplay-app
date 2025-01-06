package com.alexius.core.domain.usecases.app_entry

import com.alexius.core.data.manager.AuthResponse
import com.alexius.core.domain.manager.AuthenticationManager
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SignInWithEmail @Inject constructor(
    private val authManager: AuthenticationManager
) {

    operator fun invoke(email: String, password: String): Flow<AuthResponse> {
        return authManager.signInWithEmailPassword(email, password)
    }

}