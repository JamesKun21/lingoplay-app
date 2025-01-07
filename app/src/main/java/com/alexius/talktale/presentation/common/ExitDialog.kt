package com.alexius.talktale.presentation.common

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.alexius.talktale.presentation.navgraph_entry.NavGraphEntry
import com.alexius.talktale.ui.theme.Grey
import com.alexius.talktale.ui.theme.TalkTaleTheme
import com.alexius.talktale.ui.theme.White
import com.alexius.talktale.ui.theme.WhitePale

@Composable
fun ExitDialog(
    modifier: Modifier = Modifier,
    onExitClick: () -> Unit,
    showExitDialog: MutableState<Boolean>,
    titleNotif: String,
    notifDesc: String,
    confirmButtonText: String,
    cancelButtonText: String
) {
    if (showExitDialog.value) {
        // Show Dialog
        Dialog(
            onDismissRequest = { showExitDialog.value = false },
            properties = DialogProperties(
                dismissOnBackPress = true,
                dismissOnClickOutside = true
            ),
            content = {
                Surface(
                    modifier = modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    shape = RoundedCornerShape(16.dp),
                    color = WhitePale
                ){
                    Column(
                        modifier = modifier
                            .fillMaxWidth()
                            .padding(vertical = 24.dp)
                            .padding(horizontal = 16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                    ){
                        Text(
                            text = titleNotif,
                            style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.SemiBold),
                            textAlign = TextAlign.Center
                        )

                        Spacer(modifier = modifier.fillMaxWidth().height(12.dp))

                        Text(
                            text = notifDesc,
                            style = MaterialTheme.typography.bodySmall,
                            textAlign = TextAlign.Center
                        )

                        Spacer(modifier = modifier.fillMaxWidth().height(24.dp))

                        Row(
                            modifier = modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceEvenly
                        ){
                            Button(
                                onClick = {
                                    showExitDialog.value = false
                                    onExitClick()
                                },
                                modifier = modifier
                                    .weight(1f)
                                    .padding(end = 16.dp),
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = Color.Transparent
                                ),
                                border = BorderStroke(1.dp, Grey)
                            ) {
                                Text(
                                    text = confirmButtonText,
                                    style = MaterialTheme.typography.bodySmall.copy(fontWeight = FontWeight.SemiBold),
                                    textAlign = TextAlign.Center
                                )
                            }

                            Button(
                                onClick = { showExitDialog.value = false },
                                modifier = Modifier
                                    .weight(1f)
                                    .padding(start = 8.dp),
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = MaterialTheme.colorScheme.primary
                                )
                            ) {
                                Text(
                                    text = cancelButtonText,
                                    style = MaterialTheme.typography.bodySmall.copy(fontWeight = FontWeight.SemiBold, color = White),
                                    textAlign = TextAlign.Center
                                )
                            }
                        }
                    }
                }
            }
        )
    }
}

@Preview(showBackground = true, backgroundColor = 0xFFFFFFFF)
@Composable
private fun ExitDialogPrev() {
    TalkTaleTheme {
        var showExitDialog = remember { mutableStateOf(true) }

        ExitDialog(
            onExitClick = {},
            showExitDialog = showExitDialog ,
            titleNotif = "Kamu yakin ingin keluar?",
            notifDesc = "Jawaban kamu akan hilang dan kamu harus memulai tes dari awal.",
            confirmButtonText = "Yakin",
            cancelButtonText = "Tidak"
        )
    }
}