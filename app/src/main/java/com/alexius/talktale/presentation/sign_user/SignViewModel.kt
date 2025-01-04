package com.alexius.talktale.presentation.sign_user

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alexius.core.domain.usecases.app_entry.ReadAppEntry
import com.alexius.core.domain.usecases.app_entry.SaveAppEntry
import kotlinx.coroutines.launch
import javax.inject.Inject

class SignViewModel @Inject constructor(
    private val saveAppEntryUsecase: SaveAppEntry
): ViewModel(){

    private val _signInState = mutableStateOf(SignInState())
    val signInState: State<SignInState> = _signInState

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
        }
    }

    private fun saveAppEntry() {
        viewModelScope.launch {
            saveAppEntryUsecase()
        }
    }
}