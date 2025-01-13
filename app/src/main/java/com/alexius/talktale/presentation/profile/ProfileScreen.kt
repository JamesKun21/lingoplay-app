package com.alexius.talktale.presentation.profile

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.outlined.CalendarMonth
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.alexius.talktale.R
import com.alexius.talktale.presentation.common.MainButton
import com.alexius.talktale.presentation.sign_user.components.EmptyOutlineInputField
import com.alexius.talktale.presentation.sign_user.components.PhoneInputField
import com.alexius.talktale.ui.theme.Black
import com.alexius.talktale.ui.theme.Grey
import com.alexius.talktale.ui.theme.RedError
import com.alexius.talktale.ui.theme.White
import com.alexius.talktale.ui.theme.WhitePale
import com.rejowan.ccpc.CCPUtils
import com.rejowan.ccpc.Country
import com.rejowan.ccpc.CountryCodePickerTextField
import com.rejowan.ccpc.ViewCustomization

@Composable
fun ProfileScreen(
    modifier: Modifier = Modifier,
    userName: String,
    category: String,
    birthDate: String,
    phoneNumber: String,
    onSignedOut: () -> Unit,
    profileScreenViewModel: ProfileScreenViewModel = hiltViewModel()
) {
    val scrollState = rememberScrollState()

    var country by remember { mutableStateOf(Country.Indonesia) }

    if (!LocalInspectionMode.current) {
        CCPUtils.getCountryAutomatically(context = LocalContext.current).let {
            it?.let {
                country = Country.Indonesia
            }
        }
    }

    Surface(
        modifier = modifier
            .fillMaxSize(),
        color = White
    ){

        Column(
            modifier = modifier
                .fillMaxSize()
                .verticalScroll(enabled = true, state = scrollState)
                .padding(horizontal = 20.dp)
        ){

            Spacer(modifier = modifier.height(44.dp))

            Box(
                modifier = modifier
                    .fillMaxWidth()
                    .height(62.dp),
                contentAlignment = Alignment.Center
            ){
                Text(
                    text = "Profil",
                    style = MaterialTheme.typography.displayMedium.copy(fontWeight = FontWeight.Bold),
                    color = MaterialTheme.colorScheme.primary,
                    fontSize = 28.sp,
                    textAlign = TextAlign.Center
                )
            }

            Card(
                modifier = modifier
                    .fillMaxWidth(),
                elevation = CardDefaults.cardElevation(
                    defaultElevation = 5.dp
                ),
                colors = CardDefaults.cardColors(
                    containerColor = WhitePale
                ),
                shape = MaterialTheme.shapes.extraLarge
            ) {
                Row(
                    modifier = modifier
                        .fillMaxSize()
                        .padding(20.dp)
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.momoji),
                        modifier = modifier
                            .size(70.dp),
                        contentDescription = null
                    )

                    Spacer(modifier = modifier.width(20.dp))

                    Column(
                        modifier = modifier
                            .fillMaxSize()
                            .padding(vertical = 10.dp)
                    ) {
                        Row(
                            modifier = modifier.fillMaxWidth()
                        ) {
                            Text(
                                text = "Halo, ",
                                style = MaterialTheme.typography.bodySmall
                            )

                            Text(
                                text = "$userName!",
                                style = MaterialTheme.typography.bodySmall.copy(fontWeight = FontWeight.Bold)
                            )
                        }

                        Spacer(modifier = modifier.height(10.dp))

                        Row(
                            modifier = modifier.fillMaxWidth()
                        ) {
                            Text(
                                text = "Level kamu : ",
                                style = MaterialTheme.typography.labelSmall.copy(color = MaterialTheme.colorScheme.primary)
                            )

                            Text(
                                text = category,
                                style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.SemiBold, color = MaterialTheme.colorScheme.primary)
                            )
                        }
                    }
                }
            }

            Spacer(modifier = modifier.height(10.dp))

            Text(
                text = "Nama Lengkap",
                style = MaterialTheme.typography.labelSmall,
            )

            Spacer(modifier = modifier.height(8.dp))

            Column(modifier = modifier.fillMaxWidth()) {
                OutlinedTextField(
                    value = userName,
                    onValueChange = {},
                    readOnly = true,
                    placeholder = {
                        Text(
                            text = "",
                            style = MaterialTheme.typography.bodySmall.copy(color = Grey)
                        )
                    },
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Text
                    ),
                    trailingIcon = {},
                    isError = false,
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth(),
                    shape = MaterialTheme.shapes.medium,
                    textStyle = MaterialTheme.typography.bodySmall,
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = Black,
                        disabledContainerColor = WhitePale,
                        focusedContainerColor = WhitePale,
                        unfocusedContainerColor = WhitePale,
                        unfocusedBorderColor = Grey,
                        errorBorderColor = RedError
                    ),
                )
            }

            Spacer(modifier = modifier.height(10.dp))

            Text(
                text = "Email",
                style = MaterialTheme.typography.labelSmall,
            )

            Spacer(modifier = modifier.height(8.dp))

            Column(modifier = modifier.fillMaxWidth()) {
                OutlinedTextField(
                    value = profileScreenViewModel.email,
                    onValueChange = {},
                    readOnly = true,
                    placeholder = {
                        Text(
                            text = "",
                            style = MaterialTheme.typography.bodySmall.copy(color = Grey)
                        )
                    },
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Text
                    ),
                    trailingIcon = {},
                    isError = false,
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth(),
                    shape = MaterialTheme.shapes.medium,
                    textStyle = MaterialTheme.typography.bodySmall,
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = Black,
                        disabledContainerColor = WhitePale,
                        focusedContainerColor = WhitePale,
                        unfocusedContainerColor = WhitePale,
                        unfocusedBorderColor = Grey,
                        errorBorderColor = RedError
                    ),
                )
            }

            Spacer(modifier = modifier.height(10.dp))

            Text(
                text = "Tanggal Lahir",
                style = MaterialTheme.typography.labelSmall,
            )

            Spacer(modifier = modifier.height(8.dp))

            Column(modifier = modifier.fillMaxWidth()) {
                OutlinedTextField(
                    value = birthDate,
                    onValueChange = {},
                    readOnly = true,
                    placeholder = {
                        Text(
                            text = "",
                            style = MaterialTheme.typography.bodySmall.copy(color = Grey)
                        )
                    },
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Text
                    ),
                    trailingIcon = {
                        Icon(
                            imageVector = Icons.Outlined.CalendarMonth,
                            contentDescription = null,
                            tint = Grey
                        )
                    },
                    isError = false,
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth(),
                    shape = MaterialTheme.shapes.medium,
                    textStyle = MaterialTheme.typography.bodySmall,
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = Black,
                        disabledContainerColor = WhitePale,
                        focusedContainerColor = WhitePale,
                        unfocusedContainerColor = WhitePale,
                        unfocusedBorderColor = Grey,
                        errorBorderColor = RedError
                    ),
                )
            }

            Spacer(modifier = modifier.height(10.dp))

            Text(
                text = "Nomor Telepon",
                style = MaterialTheme.typography.labelSmall,
            )

            Spacer(modifier = modifier.height(8.dp))

            CountryCodePickerTextField(
                modifier = modifier
                    .fillMaxWidth(),
                enabled = true,
                textStyle = MaterialTheme.typography.bodySmall,
                trailingIcon = {
                },
                showError = true,
                shape = MaterialTheme.shapes.medium,
                onValueChange = { code, value, valid ->
                },
                number = phoneNumber.substring(1),
                showSheet = true,
                selectedCountry = country,
                viewCustomization = ViewCustomization(
                    showCountryCode = true
                ),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Black,
                    unfocusedBorderColor = Grey,
                    errorBorderColor = RedError,
                    disabledContainerColor = WhitePale,
                    focusedContainerColor = WhitePale,
                    unfocusedContainerColor = WhitePale,
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

            Spacer(modifier = modifier.height(29.dp))

            MainButton(
                modifier = modifier.padding(horizontal = 20.dp),
                text = "Keluar",
                onClick = {
                    profileScreenViewModel.signOut()
                    onSignedOut()
                },
                enabled = true
            )
        }
    }
}