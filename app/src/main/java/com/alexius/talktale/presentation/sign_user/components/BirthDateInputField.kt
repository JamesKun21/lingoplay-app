package com.alexius.talktale.presentation.sign_user.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.CalendarMonth
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDefaults
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.alexius.talktale.ui.theme.Green
import com.alexius.talktale.ui.theme.Grey
import com.alexius.talktale.ui.theme.LightGreen
import com.alexius.talktale.ui.theme.Orange
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BirthDateInputField(
    modifier: Modifier = Modifier,
    placeHolderText: String,
    inputText: String,
    onValueChange: (String) -> Unit,
    onDateConfirm: (String) -> Unit
) {
    val date = remember {
        Calendar.getInstance().timeInMillis
    }

    val datePickerState = rememberDatePickerState(
        initialSelectedDateMillis = date,
        yearRange = 1990..2023
    )
    var showDatePicker by rememberSaveable() { mutableStateOf(false) }

    EmptyOutlineInputField(
        placeHolderText = placeHolderText,
        inputText = inputText,
        onValueChange = onValueChange,
        trailingIcon = {
            IconButton(onClick = { showDatePicker = !showDatePicker }) {
                Icon(
                    Icons.Outlined.CalendarMonth,
                    contentDescription = "Choose birth date",
                    tint = Grey
                )
            }
        }
    )

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
                        showDatePicker = false
                    }
                ) { Text(
                    text = "Pilih",
                    style = MaterialTheme.typography.bodySmall.copy(color = Grey)
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
                    style = MaterialTheme.typography.bodySmall.copy(color = Grey)
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
                    selectedDayContentColor = Green,
                    dayContentColor = Green,
                    selectedDayContainerColor = Green,
                )
            )
        }
    }
}