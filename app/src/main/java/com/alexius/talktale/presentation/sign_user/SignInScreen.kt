package com.alexius.talktale.presentation.sign_user

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.alexius.core.data.manager.AuthResponse
import com.alexius.core.util.UIState
import com.alexius.talktale.presentation.common.LoadingScreen
import com.alexius.talktale.presentation.sign_user.components.EmailInputField
import com.alexius.talktale.presentation.sign_user.components.OptionalTextHint
import com.alexius.talktale.presentation.sign_user.components.PasswordInputField
import com.alexius.talktale.presentation.sign_user.components.SignAndGoogleButton
import com.alexius.talktale.ui.theme.TalkTaleTheme

@Composable
fun SignInScreen(
    modifier: Modifier = Modifier,
    event: (SignEvent) -> Unit,
    state: SignInState,
    uiState: UIState<AuthResponse>,
    onSignUpButtonClick: () -> Unit,
) {
    val scrollState = rememberScrollState()
    var isLoading by remember { mutableStateOf(false) }
    var emailIsValid by remember { mutableStateOf(false) }

    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(enabled = true, state = scrollState),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = modifier
            .fillMaxWidth()
            .height(74.dp))

        OptionalTextHint(
            title = "Masuk",
            hintQuestionText = "Belum punya akun?",
            hintActionText = " Daftar",
            onHintActionClick = { onSignUpButtonClick() }
        )

        Spacer(modifier = modifier
            .fillMaxWidth()
            .height(33.dp))

        EmailInputField(
            email = state.email,
            onValueChange = { event(SignEvent.UpdateEmail(it)) },
            onEmailAddressNotValid = { emailIsValid = false },
            onEmailAddressValid = { emailIsValid = true }
        )

        Spacer(modifier = modifier
            .fillMaxWidth()
            .height(16.dp))

        PasswordInputField(
            text = state.password,
            onTextChanged = { event(SignEvent.UpdatePassword(it)) },
        )

        Spacer(modifier = modifier
            .fillMaxWidth()
            .height(30.dp))

        SignAndGoogleButton(
            enableSignButton = emailIsValid && state.password.isNotEmpty() && uiState !is UIState.Loading,
            onSignButtonClick = { event(SignEvent.SignInWithEmail) },
            signButtonText = "Masuk",
            onGoogleButtonClick = { event(SignEvent.SignInWIthGoogle) },
            enableGoogleButton = uiState !is UIState.Loading
        )
    }

    LoadingScreen(enableLoading = isLoading)

    when (uiState) {
        is UIState.Loading -> {
            isLoading = true
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

@Preview(showBackground = true, backgroundColor = 0xFFFFFFFF)
@Composable
private fun SignInPrev() {
    TalkTaleTheme {
        SignInScreen(
            event = {},
            state = SignInState(),
            onSignUpButtonClick = {},
            uiState = UIState.Loading,
        )
    }
}