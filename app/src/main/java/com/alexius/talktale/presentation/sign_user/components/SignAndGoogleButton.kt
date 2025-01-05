package com.alexius.talktale.presentation.sign_user.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.alexius.talktale.ui.theme.DarkGrey
import com.alexius.talktale.ui.theme.Grey
import com.alexius.talktale.ui.theme.TalkTaleTheme
import com.alexius.talktale.ui.theme.White
import com.alexius.talktale.R
import com.alexius.talktale.ui.theme.Black
import com.alexius.talktale.ui.theme.Green50
import com.alexius.talktale.ui.theme.WhitePale

@Composable
fun SignAndGoogleButton(
    modifier: Modifier = Modifier,
    enableSignButton: Boolean = false,
    onSignButtonClick: () -> Unit,
    signButtonText: String,
    onGoogleButtonClick: () -> Unit,
    enableGoogleButton: Boolean = true,
) {

    val coroutineScope = rememberCoroutineScope()

    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 40.dp)
    ) {
        Button(
            onClick = { onSignButtonClick() },
            enabled = enableSignButton,
            modifier = modifier.fillMaxWidth().height(44.dp),
            colors = ButtonDefaults.buttonColors(
                disabledContainerColor = Green50,
            ),
        ) {
            Text(
                text = signButtonText,
                style = MaterialTheme.typography.bodySmall.copy(fontWeight = FontWeight.SemiBold, color = White)
            )
        }

        Spacer(modifier = modifier.height(20.dp))

        Row(
            modifier = modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp)
                .height(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Canvas(modifier.weight(1f).padding(end = 17.dp)) {
                drawLine(
                    color = Grey,
                    start = Offset(0f, 0f),
                    end = Offset(size.width, 0f),
                    cap = StrokeCap.Round,
                    strokeWidth = 2f
                )
            }
            Text(
                text = "Atau",
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.labelSmall.copy(color = DarkGrey)
            )
            Canvas(modifier.weight(1f).padding(start = 17.dp)) {
                drawLine(
                    color = Grey,
                    start = Offset(0f, 0f),
                    end = Offset(size.width, 0f),
                    cap = StrokeCap.Round,
                    strokeWidth = 2f
                )
            }
        }

        Spacer(modifier = modifier.height(20.dp))

        Button(
            onClick = {  },
            enabled = enableGoogleButton,
            modifier = modifier.fillMaxWidth().height(44.dp),
            colors = ButtonDefaults.buttonColors(containerColor = White),
        ) {
            Row(
                modifier = modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
            ) {
                Image(
                    painter = painterResource(id = R.drawable.google),
                    contentDescription = "Google",
                    modifier = modifier.size(18.dp),
                )

                Spacer(modifier = modifier.width(8.dp))

                Text(
                    text = "Lanjut dengan Google",
                    style = MaterialTheme.typography.bodySmall.copy(fontWeight = FontWeight.SemiBold)
                )
            }

        }
    }
}

@Preview(showBackground = true, backgroundColor = 0xFFFFFFFF)
@Composable
private fun SignAndGoogleButtonPrev() {
    TalkTaleTheme {
        SignAndGoogleButton(
            enableSignButton = false,
            onSignButtonClick = {},
            signButtonText = "Masuk",
            onGoogleButtonClick = {},
        )
    }
}