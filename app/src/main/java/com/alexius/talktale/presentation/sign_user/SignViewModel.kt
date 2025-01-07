package com.alexius.talktale.presentation.sign_user

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alexius.core.data.manager.AuthResponse
import com.alexius.core.domain.usecases.app_entry.SaveAppEntry
import com.alexius.core.domain.usecases.app_entry.SignInGoogle
import com.alexius.core.domain.usecases.app_entry.SignInWithEmail
import com.alexius.core.domain.usecases.app_entry.SignUpWithEmail
import com.alexius.core.util.UIState
import com.alexius.talktale.presentation.navgraph_main.Route
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignViewModel @Inject constructor(
    private val saveAppEntryUsecase: SaveAppEntry,
    private val signInGoogle: SignInGoogle,
    private val signUpWithEmail: SignUpWithEmail,
    private val signInWithEmail: SignInWithEmail
): ViewModel(){

    private val _signInState = mutableStateOf(SignInState())
    val signInState: State<SignInState> = _signInState

    private val _signUpState = mutableStateOf(SignUpState())
    val signUpState: State<SignUpState> = _signUpState

    private val _uiStateSignIn: MutableState<UIState<AuthResponse>> = mutableStateOf(UIState.Loading)
    val uiStateSignIn: State<UIState<AuthResponse>> = _uiStateSignIn

    private val _destinationAfterSignIn = mutableStateOf(Route.AppStartNavigation.route)

    fun onEvent(event: SignEvent) {
        when (event) {
            is SignEvent.SaveAppEntry -> {
                saveAppEntry()
            }
            is SignEvent.UpdateEmail -> {
                _signInState.value = signInState.value.copy(email = event.email)
            }
            is SignEvent.UpdatePassword -> {
                _signInState.value = signInState.value.copy(password = event.password)
            }
            is SignEvent.SignInWIthGoogle -> {
                signInWithGoogle().onEach { response ->
                    if (response is AuthResponse.Success) {
                        event.callback()
                        _uiStateSignIn.value = UIState.Success(response)
                    } else {
                        val error = response as AuthResponse.Error
                        _uiStateSignIn.value = UIState.Error(error.errorMessage)
                        delay(2000)
                        _uiStateSignIn.value = UIState.Loading
                    }
                }.launchIn(viewModelScope)
            }
            is SignEvent.SignInWithEmail -> {
                signInWithEmail(_signInState.value.email, _signInState.value.password).onEach { response ->
                    if (response is AuthResponse.Success) {
                        event.callback()
                        _uiStateSignIn.value = UIState.Success(response)
                    } else {
                        val error = response as AuthResponse.Error
                        _uiStateSignIn.value = UIState.Error(error.errorMessage)
                        delay(2000)
                        _uiStateSignIn.value = UIState.Loading
                    }
                }.launchIn(viewModelScope)
            }
            is SignEvent.UpdateEmailSignUp -> {
                _signUpState.value = signUpState.value.copy(email = event.email)
            }
            is SignEvent.UpdatePasswordSignUp -> {
                _signUpState.value = signUpState.value.copy(password = event.password)
            }
            is SignEvent.UpdateFullName -> {
                _signUpState.value = signUpState.value.copy(fullName = event.fullName)
            }
            is SignEvent.UpdateBirthDate -> {
                _signUpState.value = signUpState.value.copy(birthDate = event.birthDate)
            }
            is SignEvent.UpdatePhoneNumber -> {
                _signUpState.value = signUpState.value.copy(phoneNumber = event.phoneNumber)
            }
            is SignEvent.SignUpWithEmail -> {
                signUpWithEmail(_signUpState.value.email, _signUpState.value.password).onEach { response ->
                    if (response is AuthResponse.Success) {
                        event.callback()
                        _uiStateSignIn.value = UIState.Success(response)
                    } else {
                        val error = response as AuthResponse.Error
                        _uiStateSignIn.value = UIState.Error(error.errorMessage)
                        delay(2000)
                        _uiStateSignIn.value = UIState.Loading
                    }
                }.launchIn(viewModelScope)
            }
        }
    }

    private fun signInWithGoogle(): Flow<AuthResponse> {
        return signInGoogle()
    }

    fun saveAppEntry() {
        viewModelScope.launch {
            saveAppEntryUsecase()
        }
    }
}