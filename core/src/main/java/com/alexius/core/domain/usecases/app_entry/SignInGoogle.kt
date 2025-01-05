package com.alexius.core.domain.usecases.app_entry

import com.alexius.core.data.manager.AuthResponse
import com.alexius.core.domain.manager.AuthenticationManager
import com.alexius.core.domain.manager.LocalUserManager
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SignInGoogle @Inject constructor(
    private val authManager: AuthenticationManager
) {

    operator fun invoke(): Flow<AuthResponse> {
        return authManager.signInWithGoogle()
    }

}