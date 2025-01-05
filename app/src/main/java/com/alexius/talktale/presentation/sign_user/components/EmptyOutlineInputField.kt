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
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.alexius.talktale.ui.theme.Black
import com.alexius.talktale.ui.theme.Grey

@Composable
fun EmptyOutlineInputField(
    modifier: Modifier = Modifier,
    placeHolderText: String,
    inputText: String,
    onValueChange: (String) -> Unit,
    onInputFieldEmpty: () -> Unit = {},
    onInputFieldFilled: () -> Unit = {},
    trailingIcon: @Composable (() -> Unit)? = null,
) {
    var isError by remember { mutableStateOf(false) }

    Column(modifier = modifier.fillMaxWidth().padding(horizontal = 47.dp)) {
        OutlinedTextField(
            value = inputText,
            onValueChange = { input ->
                onValueChange(input)
                isError = input.isEmpty()
                if (isError) {
                    onInputFieldEmpty()
                } else {
                    onInputFieldFilled()
                }
            },
            placeholder = {
                Text(
                    text = placeHolderText,
                    style = MaterialTheme.typography.bodySmall.copy(color = Grey)
                )
            },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Text
            ),
            trailingIcon = trailingIcon,
            isError = isError,
            singleLine = true,
            modifier = Modifier.fillMaxWidth(),
            shape = MaterialTheme.shapes.medium,
            textStyle = MaterialTheme.typography.bodySmall,
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = Black,
                unfocusedBorderColor = Grey,
            ),
        )
    }
}