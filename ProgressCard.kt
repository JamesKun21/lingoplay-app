package com.alexius.talktale.presentation.home_screen.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.alexius.talktale.R
import com.alexius.talktale.ui.theme.LightOrange
import com.alexius.talktale.ui.theme.TalkTaleTheme
import com.alexius.talktale.ui.theme.WhitePale

@Composable
fun ProgressCard(
    modifier: Modifier = Modifier,
    onClickNext: () -> Unit
) {
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
    ){
        Column(
            modifier = modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Row(
                modifier = modifier
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    painter = painterResource(id = R.drawable.thanks_girl),
                    modifier = modifier.size(77.dp),
                    contentDescription = null
                )

                Spacer(modifier = modifier.width(25.dp))

                Column(
                    modifier = modifier.fillMaxWidth()
                ) {
                    Text(
                        text = "Minggu ke-1",
                        style = MaterialTheme.typography.bodySmall.copy(fontWeight = FontWeight.SemiBold)
                    )

                    Spacer(modifier = modifier.height(4.dp))

                    Text(
                        text = "Kamu bisa mengecek hasil progress cerita yang telah kamu selesaikan di sini",
                        style = MaterialTheme.typography.labelSmall
                    )
                }
            }

            Spacer(modifier = modifier.height(10.dp))

            Button(
                onClick = onClickNext,
                modifier = modifier
                    .fillMaxWidth()
                    .padding(horizontal = 12.dp)
                    .height(44.dp),
                shape = MaterialTheme.shapes.extraLarge,
                colors = ButtonDefaults.buttonColors(
                    containerColor = LightOrange
                )
            ){
                Text(
                    text = "Lihat selengkapnya",
                    style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.SemiBold, color = MaterialTheme.colorScheme.secondary),
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}

@Preview(showBackground = true, backgroundColor = 0xFFFFFFFF)
@Composable
private fun Prev() {
    TalkTaleTheme {
        ProgressCard(
            onClickNext = {}
        )
    }
}