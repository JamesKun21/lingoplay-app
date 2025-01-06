package com.alexius.talktale.presentation.sign_user.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.alexius.talktale.presentation.navgraph_entry.NavGraphEntry
import com.alexius.talktale.ui.theme.Black
import com.alexius.talktale.ui.theme.Green
import com.alexius.talktale.ui.theme.Grey
import com.alexius.talktale.ui.theme.LightGreen
import com.alexius.talktale.ui.theme.Poppins
import com.alexius.talktale.ui.theme.TalkTaleTheme

@Composable
fun PasswordInputField(
    text: String,
    modifier: Modifier = Modifier,
    semanticContentDescription: String = "",
    validateStrengthPassword: Boolean = false,
    onHasStrongPassword: (isStrong: Boolean) -> Unit = {},
    onTextChanged: (text: String) -> Unit,
) {
    val focusManager = LocalFocusManager.current
    var showPassword by remember { mutableStateOf(false) }
    var error by remember { mutableStateOf(false) }

    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 47.dp)
    ) {
        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth()
                .semantics{ contentDescription = semanticContentDescription },
            value = text,
            onValueChange = onTextChanged,
            placeholder = {
                Text(
                    text = "Kata sandi",
                    style = MaterialTheme.typography.bodySmall.copy(color = Grey)
                )
            },
            keyboardOptions = KeyboardOptions.Default.copy(
                autoCorrectEnabled = true,
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Done
            ),
            keyboardActions = KeyboardActions(
                onDone = {
                    focusManager.clearFocus()
                }
            ),
            shape = MaterialTheme.shapes.medium,
            singleLine = true,
            isError = error,
            visualTransformation = if (showPassword) VisualTransformation.None else PasswordVisualTransformation(),
            trailingIcon = {
                val (icon, iconColor) = if (showPassword) {
                    Pair(
                        Icons.Filled.Visibility,
                        second = Black
                    )
                } else {
                    Pair(Icons.Filled.VisibilityOff, Grey)
                }

                IconButton(onClick = { showPassword = !showPassword }) {
                    Icon(
                        icon,
                        contentDescription = "Visibility",
                        tint = iconColor
                    )
                }
            },
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = Black,
                unfocusedBorderColor = Grey,
            )
        )
        Spacer(modifier = Modifier.height(8.dp))
        if (validateStrengthPassword && text.isNotEmpty()) {
            val strengthPasswordType = strengthChecker(text)
            if (strengthPasswordType == StrengthPasswordTypes.STRONG) {
                onHasStrongPassword(true)
                error = false
            } else {
                onHasStrongPassword(false)
                error = true
            }
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .semantics { contentDescription = "StrengthPasswordMessage" },
                text = buildAnnotatedString {
                    withStyle(
                        style = SpanStyle(
                            color = Black,
                            fontSize = MaterialTheme.typography.labelSmall.fontSize,
                            fontFamily = Poppins
                        )
                    ) {
                        append("Tingkat kata sandi: ")
                        withStyle(style = SpanStyle(color = Green)) {
                            if (strengthPasswordType == StrengthPasswordTypes.STRONG) {
                                append("Kuat")
                            }
                        }
                        withStyle(style = SpanStyle(color = MaterialTheme.colorScheme.error)) {
                            if (strengthPasswordType == StrengthPasswordTypes.WEAK) {
                                append("Lemah")
                            }
                        }
                    }
                }
            )
        }
    }
}

@Preview(showBackground = true, backgroundColor = 0xFFFFFFFF)
@Composable
private fun PasswordInputFieldPrev() {
    TalkTaleTheme {
        var password by remember { mutableStateOf("") }
        
        PasswordInputField(
            text = password,
            onTextChanged = {
                password = it
            },
            validateStrengthPassword = true,
        )
    }
}

private fun strengthChecker(password: String): StrengthPasswordTypes =
    when {
        REGEX_STRONG_PASSWORD.toRegex().containsMatchIn(password) -> StrengthPasswordTypes.STRONG
        else -> StrengthPasswordTypes.WEAK
    }

enum class StrengthPasswordTypes {
    STRONG,
    WEAK
}

private const val REGEX_STRONG_PASSWORD =
    "(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9])(?=.*[^A-Za-z0-9])(?=.{8,})"