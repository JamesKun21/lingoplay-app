package com.alexius.talktale.presentation.sign_user.components

import android.util.Log
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.alexius.talktale.presentation.navgraph_entry.NavGraphEntry
import com.alexius.talktale.ui.theme.Black
import com.alexius.talktale.ui.theme.Grey
import com.alexius.talktale.ui.theme.RedError
import com.alexius.talktale.ui.theme.TalkTaleTheme
import com.rejowan.ccpc.CCPUtils
import com.rejowan.ccpc.CCPValidator
import com.rejowan.ccpc.Country
import com.rejowan.ccpc.CountryCodePickerTextField
import com.rejowan.ccpc.ViewCustomization

@Composable
fun PhoneInputField(
    modifier: Modifier = Modifier,
    phoneNumber: String,
    onValueChange: (String) -> Unit,
    phoneNumberIsValid: (Boolean) -> Unit
) {

    var text by remember { mutableStateOf(phoneNumber) }

    var country by remember { mutableStateOf(Country.Indonesia) }

    if (!LocalInspectionMode.current) {
        CCPUtils.getCountryAutomatically(context = LocalContext.current).let {
            it?.let {
                country = it
            }
        }
    }

    CountryCodePickerTextField(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 47.dp),
        enabled = true,
        textStyle = MaterialTheme.typography.bodySmall,
        trailingIcon = {
            IconButton(onClick = {
                text = ""
                onValueChange("")
            }) {
                Icon(
                    imageVector = Icons.Default.Clear, contentDescription = "Clear",
                    tint = Grey
                )
            }
        },
        showError = true,
        shape = MaterialTheme.shapes.medium,
        onValueChange = { code, value, valid ->
            text = value
            onValueChange(value)
            phoneNumberIsValid(valid)
        },
        number = text,
        showSheet = true,
        selectedCountry = country,
        viewCustomization = ViewCustomization(
            showCountryCode = true
        ),
        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = Black,
            unfocusedBorderColor = Grey,
            errorBorderColor = RedError
        ),
        placeholder = {
            Text(
                text = "Nomor telepon",
                style = MaterialTheme.typography.bodySmall.copy(color = Grey)
            )
        },
        keyboardOptions = KeyboardOptions.Default.copy(
            autoCorrectEnabled = false,
            keyboardType = KeyboardType.Number,
            imeAction = ImeAction.Done
        )
    )
}

@Preview(showBackground = true, backgroundColor = 0xFFFFFFFF)
@Composable
private fun PhoneInputFieldPrev() {
    TalkTaleTheme {

        var phoneNumber by remember { mutableStateOf("") }

        LaunchedEffect(phoneNumber) {
            Log.d("PhoneInputField", "Phone number: $phoneNumber")
        }

        PhoneInputField(
            onValueChange = {
                phoneNumber = it
            },
            phoneNumberIsValid = { },
            phoneNumber = phoneNumber
        )
    }
}

