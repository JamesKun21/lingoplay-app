package com.alexius.core.data.manager

import android.app.Application
import android.util.Log
import androidx.credentials.CredentialManager
import androidx.credentials.CustomCredential
import androidx.credentials.GetCredentialRequest
import com.alexius.core.domain.manager.AuthenticationManager
import com.alexius.core.util.Constants.WEB_CLIENT_ID
import com.google.android.libraries.identity.googleid.GetGoogleIdOption
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential
import com.google.android.libraries.identity.googleid.GoogleIdTokenParsingException
import com.google.firebase.Firebase
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.auth
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import java.security.MessageDigest
import java.util.UUID
import javax.inject.Inject

class AuthenticationManagerImplementation @Inject constructor(
    private val context: Application
) : AuthenticationManager {

    private val auth = Firebase.auth

    override fun createAccountWithEmailPassword(
        email: String,
        password: String
    ): Flow<AuthResponse> =
        callbackFlow {
            auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        trySend(AuthResponse.Success)
                    } else {
                        trySend(AuthResponse.Error(task.exception?.message ?: "Unknown error"))
                        Log.d(
                            "AuthenticationManager",
                            "createAccountWithEmailPassword: ${task.exception?.message}"
                        )
                    }
                }
            awaitClose()
        }

    override fun signInWithEmailPassword(email: String, password: String): Flow<AuthResponse> =
        callbackFlow {
            auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        trySend(AuthResponse.Success)
                    } else {
                        trySend(AuthResponse.Error(task.exception?.message ?: "Unknown error"))
                        Log.d(
                            "AuthenticationManager",
                            "signInWithEmailPassword: ${task.exception?.message}"
                        )
                    }
                }
            awaitClose()
        }

    private fun createNonce(): String {
        val rawNonce = UUID.randomUUID().toString()
        val bytes = rawNonce.toByteArray()
        val md = MessageDigest.getInstance("SHA-256")
        val digest = md.digest(bytes)

        return digest.fold("") { str, it ->
            str + "%02x".format(it)
        }
    }

    override fun signInWithGoogle(): Flow<AuthResponse> =
        callbackFlow {
            val googleIdOption = GetGoogleIdOption.Builder()
                .setFilterByAuthorizedAccounts(false)
                .setServerClientId(WEB_CLIENT_ID)
                .setAutoSelectEnabled(true)
                .setNonce(createNonce())
                .build()

            val request = GetCredentialRequest.Builder()
                .addCredentialOption(googleIdOption)
                .build()

            try {
                val credentialManager = CredentialManager.create(context)
                val result = credentialManager.getCredential(
                    context,
                    request
                )

                val credential = result.credential
                if (credential is CustomCredential) {
                    if (credential.type == GoogleIdTokenCredential.TYPE_GOOGLE_ID_TOKEN_CREDENTIAL) {
                        try {
                            val googleIdTokenCredential = GoogleIdTokenCredential
                                .createFrom(credential.data)

                            val firebaseCredential = GoogleAuthProvider
                                .getCredential(
                                    googleIdTokenCredential.idToken,
                                    null
                                )

                            auth.signInWithCredential(firebaseCredential)
                                .addOnCompleteListener {
                                    if (it.isSuccessful) {
                                        trySend(AuthResponse.Success)
                                    } else {
                                        trySend(
                                            AuthResponse.Error(
                                                it.exception?.message ?: "Unknown error"
                                            )
                                        )
                                    }
                                }
                        } catch (e: GoogleIdTokenParsingException) {
                            trySend(AuthResponse.Error(errorMessage = e.message ?: ""))
                        }
                    }
                }

            } catch (e: Exception) {
                trySend(AuthResponse.Error(e.message ?: "Unknown error"))
                Log.d("AuthenticationManager", "signInWithGoogle: ${e.message}")
            }

            awaitClose()
        }
}

interface AuthResponse{
    data object Success: AuthResponse
    data class Error(val errorMessage: String): AuthResponse
}