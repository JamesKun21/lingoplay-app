package com.alexius.talktale.presentation.sign_user.components

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.CalendarMonth
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDefaults
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.alexius.talktale.presentation.sign_user.SignViewModel
import com.alexius.talktale.ui.theme.Black
import com.alexius.talktale.ui.theme.Green
import com.alexius.talktale.ui.theme.Grey
import com.alexius.talktale.ui.theme.LightGreen
import com.alexius.talktale.ui.theme.Orange
import com.alexius.talktale.ui.theme.TalkTaleTheme
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BirthDateInputField(
    modifier: Modifier = Modifier,
    placeHolderText: String = "Tanggal lahir",
    onDateConfirm: (String) -> Unit,
    onDateValid: () -> Unit = {},
    onDateInvalid: () -> Unit = {},
) {
    val date = remember {
        Calendar.getInstance().timeInMillis
    }

    var isError by rememberSaveable() { mutableStateOf(false) }

    var dateTemporaryValidContainer by rememberSaveable { mutableStateOf("") }

    val datePickerState = rememberDatePickerState(
        initialSelectedDateMillis = date,
        yearRange = 1900..Calendar.getInstance().get(Calendar.YEAR)
    )
    var showDatePicker by rememberSaveable() { mutableStateOf(false) }

    val viewModel: SignViewModel = hiltViewModel()

    LaunchedEffect(dateTemporaryValidContainer) {

    }

    Column(modifier = modifier.fillMaxWidth().padding(horizontal = 47.dp)) {

        OutlinedTextField(
            value = dateTemporaryValidContainer,
            onValueChange = {
                val data= viewModel.signUpState.value.birthDate
                Log.d("BirthDateInputField", "Birth Date: $data")
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
            isError = isError,
            singleLine = true,
            modifier = Modifier.fillMaxWidth(),
            shape = MaterialTheme.shapes.medium,
            textStyle = MaterialTheme.typography.bodySmall,
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = Black,
                unfocusedBorderColor = Grey,
            ),
            trailingIcon = {
                IconButton(
                    onClick = { showDatePicker = !showDatePicker }
                ){
                    Icon(
                        Icons.Outlined.CalendarMonth,
                        contentDescription = "Choose birth date",
                        tint = Grey
                    )
                }
            }
        )
    }

    if (showDatePicker) {
        DatePickerDialog(
            onDismissRequest = { showDatePicker = false },
            confirmButton = {
                TextButton(
                    onClick = {
                        val selectedDate = Calendar.getInstance().apply {
                            timeInMillis = datePickerState.selectedDateMillis!!
                        }
                        val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
                        onDateConfirm(dateFormat.format(selectedDate.time))
                        dateTemporaryValidContainer = dateFormat.format(selectedDate.time)
                        showDatePicker = false

                        isError =  dateTemporaryValidContainer.isEmpty()
                        if (isError) {
                            onDateInvalid
                        } else {
                            onDateValid
                        }
                    }
                ) { Text(
                    text = "Pilih",
                    style = MaterialTheme.typography.bodySmall.copy(color = Black)
                )
                }
            },
            dismissButton = {
                TextButton(
                    onClick = {
                        showDatePicker = false
                    }
                ) { Text(
                    text = "Batal",
                    style = MaterialTheme.typography.bodySmall.copy(color = Black)
                )  }
            },
            colors = DatePickerDefaults.colors(
                containerColor = LightGreen,
            )
        )
        {
            DatePicker(
                state = datePickerState,
                colors = DatePickerDefaults.colors(
                    todayContentColor = Orange,
                    todayDateBorderColor = Green,
                    selectedDayContentColor = Orange,
                    dayContentColor = Green,
                    selectedDayContainerColor = Green,
                )
            )
        }
    }
}

@Preview(showBackground = true, backgroundColor = 0xFFFFFFFF, device = Devices.PIXEL_4)
@Composable
private fun BirthInputPrev() {
    TalkTaleTheme {
        var birthDate by remember { mutableStateOf("") }

        BirthDateInputField(
            onDateConfirm = {}
        )
    }
}