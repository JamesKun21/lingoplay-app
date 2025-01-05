package com.alexius.core.domain.manager

import com.alexius.core.data.manager.AuthResponse
import kotlinx.coroutines.flow.Flow

interface AuthenticationManager {

    fun createAccountWithEmailPassword(email: String, password: String): Flow<AuthResponse>

    fun signInWithEmailPassword(email: String, password: String): Flow<AuthResponse>

    fun signInWithGoogle(): Flow<AuthResponse>
}