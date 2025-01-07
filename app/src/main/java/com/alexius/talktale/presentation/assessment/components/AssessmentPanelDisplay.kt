package com.alexius.talktale.presentation.assessment.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.alexius.talktale.R
import com.alexius.talktale.presentation.navgraph_entry.NavGraphEntry
import com.alexius.talktale.ui.theme.TalkTaleTheme

@Composable
fun AssessmentPanelDisplay(
    modifier: Modifier = Modifier,
    title: String,
    underTitle: String,
    drawableImage: Int,
    onClickMainButton: () -> Unit,
    mainButtonText: String,
    hints: List<String>
) {
    val scrollState = rememberScrollState()

    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll( enabled = true, state = scrollState),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Column(
            modifier = modifier.fillMaxWidth().padding(24.dp)
        ) {

            Spacer(modifier = modifier
                .fillMaxWidth()
                .height(30.dp))

            Box(
                modifier = modifier.fillMaxWidth(),
                contentAlignment = Alignment.Center
            ){
                Text(
                    text = title,
                    style = MaterialTheme.typography.displayMedium.copy(fontSize = 28.sp, color = MaterialTheme.colorScheme.primary),
                    textAlign = TextAlign.Center,
                    fontWeight = FontWeight.Bold,
                    modifier = modifier.fillMaxWidth(),
                )
            }

            Box(
                modifier = modifier.fillMaxWidth().height(22.dp),
                contentAlignment = Alignment.Center
            ){
                Text(
                    text = underTitle,
                    style = MaterialTheme.typography.labelSmall,
                    textAlign = TextAlign.Center,
                    modifier = modifier.fillMaxWidth().padding(horizontal = 15.dp)
                )
            }
        }

        Spacer(modifier = modifier
            .fillMaxWidth()
            .height(20.dp))

        Image(
            painter = painterResource(id = drawableImage),
            contentDescription = null,
            modifier = modifier.size(274.dp),
            contentScale = ContentScale.Crop
        )

        Spacer(modifier = modifier
            .fillMaxWidth()
            .height(20.dp))

        Column(
            modifier = modifier.fillMaxWidth().padding(51.dp)
        ) {
            hints.forEach {
                HintText(
                    text = it,
                )
            }
        }

        Spacer(modifier = modifier
            .fillMaxWidth()
            .height(65.dp))

        Button(
            onClick = onClickMainButton,
            modifier = modifier
                .fillMaxWidth()
                .padding(horizontal = 40.dp)
                .height(44.dp),
            shape = MaterialTheme.shapes.extraLarge
        ){
            Text(
                text = mainButtonText,
                style = MaterialTheme.typography.bodySmall.copy(fontWeight = FontWeight.SemiBold, color = Color.White),
                textAlign = TextAlign.Center
            )
        }
    }
}

@Preview(showBackground = true, backgroundColor = 0xFFFFFFFF)
@Composable
private fun AssessmentPanelPrev() {
    TalkTaleTheme {
        AssessmentPanelDisplay(
            title = "Reading",
            underTitle = "Kamu akan memulai bagian membaca.",
            drawableImage = R.drawable.robot_reading,
            onClickMainButton = {},
            mainButtonText = "Lanjut",
            hints = listOf(
                "Pertanyaan di tes ini dapat semakin sulit atau mudah untuk menyesuaikan levelmu.",
                "Kamu tidak akan kehilangan poin jika salah menjawab.",
                "Setelah mengumpulkan jawaban, kamu tidak bisa kembali ke pertanyaan sebelumnya."
            )
        )
    }
}