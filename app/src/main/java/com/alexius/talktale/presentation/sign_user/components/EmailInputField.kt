package com.alexius.talktale.presentation.sign_user.components

import android.util.Patterns
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.alexius.talktale.ui.theme.Black
import com.alexius.talktale.ui.theme.Grey
import com.alexius.talktale.ui.theme.RedError
import com.alexius.talktale.ui.theme.TalkTaleTheme

@Composable
fun EmailInputField(
    modifier: Modifier = Modifier,
    email: String,
    onValueChange: (String) -> Unit,
    onEmailAddressNotValid: () -> Unit,
    onEmailAddressValid: () -> Unit
) {
    var isError by remember { mutableStateOf(false) }

    Column(modifier = modifier.fillMaxWidth().padding(horizontal = 47.dp)) {
        OutlinedTextField(
            value = email,
            onValueChange = { input ->
                onValueChange(input)
                isError = !Patterns.EMAIL_ADDRESS.matcher(input).matches()
                if (isError) {
                    onEmailAddressNotValid()
                } else {
                    onEmailAddressValid()
                }
            },
            placeholder = {
                Text(
                    text = "Email",
                    style = MaterialTheme.typography.bodySmall.copy(color = Grey)
                )
            },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Email
            ),
            isError = isError,
            singleLine = true,
            modifier = Modifier.fillMaxWidth(),
            shape = MaterialTheme.shapes.medium,
            textStyle = MaterialTheme.typography.bodySmall,
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = Black,
                unfocusedBorderColor = Grey,
                errorBorderColor = RedError
            ),
        )
    }
}

@Preview(showBackground = true, backgroundColor = 0xFFFFFFFF)
@Composable
private fun EmailInputFieldPrev() {
    TalkTaleTheme {
        var email by remember { mutableStateOf("") }

        EmailInputField(
            email = email,
            onValueChange = {
                email = it
            },
            onEmailAddressNotValid = { },
            onEmailAddressValid = {}
        )
    }
}