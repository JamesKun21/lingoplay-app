package com.alexius.talktale.presentation.sign_user

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.alexius.core.data.manager.AuthResponse
import com.alexius.core.util.UIState
import com.alexius.talktale.presentation.sign_user.components.BirthDateInputField
import com.alexius.talktale.presentation.sign_user.components.EmailInputField
import com.alexius.talktale.presentation.sign_user.components.EmptyOutlineInputField
import com.alexius.talktale.presentation.sign_user.components.OptionalTextHint
import com.alexius.talktale.presentation.sign_user.components.PhoneInputField
import com.alexius.talktale.presentation.sign_user.components.SignAndGoogleButton
import com.alexius.talktale.ui.theme.TalkTaleTheme

@Composable
fun SignUoScreen(
    modifier: Modifier = Modifier,
    onSignInButtonClick: () -> Unit,
    state: SignUpState,
    event: (SignEvent) -> Unit,
    uiState: UIState<AuthResponse>
) {
    val scrollState = rememberScrollState()

    var emailIsValid by rememberSaveable { mutableStateOf(false) }
    var phoneNumberIsValid by rememberSaveable { mutableStateOf(false) }
    var isLoading by rememberSaveable { mutableStateOf(false) }
    var enableSignUpButton by rememberSaveable { mutableStateOf(false) }

    LaunchedEffect(emailIsValid, state.password, isLoading, state.fullName, state.birthDate, phoneNumberIsValid) {
        enableSignUpButton = emailIsValid && state.password.isNotEmpty() && !isLoading && state.fullName.isNotEmpty() && state.birthDate.isNotEmpty() && phoneNumberIsValid
    }

    LaunchedEffect(uiState) {
        when (uiState) {
            is UIState.Loading -> {

        }

            is UIState.Success -> {
            // Do something
            isLoading = false
        }
            is UIState.Error -> {
            // Do something
            isLoading = false
        }
        }
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(enabled = true, state = scrollState),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Spacer(modifier = modifier
            .fillMaxWidth()
            .height(104.dp))

        OptionalTextHint(
            title = "Daftar",
            hintQuestionText = "Sudah punya akun?",
            hintActionText = " Masuk",
            onHintActionClick = { onSignInButtonClick() }
        )

        Spacer(modifier = modifier
            .fillMaxWidth()
            .height(29.dp))

        EmptyOutlineInputField(
            placeHolderText = "Nama lengkap",
            inputText = state.fullName,
            onValueChange = { event(SignEvent.UpdateFullName(it)) }
        )

        Spacer(modifier = modifier
            .fillMaxWidth()
            .height(16.dp))

        EmailInputField(
            email = state.email,
            onValueChange = { event(SignEvent.UpdateEmail(it)) },
            onEmailAddressNotValid = { emailIsValid = false },
            onEmailAddressValid = { emailIsValid = true }
        )

        Spacer(modifier = modifier
            .fillMaxWidth()
            .height(16.dp))

        BirthDateInputField(
            placeHolderText = "Tanggal lahir",
            inputText = state.birthDate,
            onValueChange = { event(SignEvent.UpdateBirthDate(it)) },
            onDateConfirm = { event(SignEvent.UpdateBirthDate(it)) }
        )

        Spacer(modifier = modifier
            .fillMaxWidth()
            .height(16.dp))

        PhoneInputField(
            phoneNumber = state.phoneNumber,
            onValueChange = { event(SignEvent.UpdatePhoneNumber(it)) },
            phoneNumberIsValid = { phoneNumberIsValid = it }
        )

        Spacer(modifier = modifier
            .fillMaxWidth()
            .height(29.dp))

        SignAndGoogleButton(
            enableSignButton = enableSignUpButton,
            onSignButtonClick = {
                event(SignEvent.SignUpWithEmail)
                isLoading = true },
            signButtonText = "Daftar",
            onGoogleButtonClick = {
                event(SignEvent.SignInWIthGoogle)
                isLoading = true
            },
            enableGoogleButton = !isLoading
        )
    }
}

@Preview(showBackground = true, backgroundColor = 0xFFFFFFFF)
@Composable
private fun SignUpScreenPrev() {
    TalkTaleTheme {
        SignUoScreen(
            onSignInButtonClick = {},
            state = SignUpState(),
            event = {},
            uiState = UIState.Loading
        )
    }
}