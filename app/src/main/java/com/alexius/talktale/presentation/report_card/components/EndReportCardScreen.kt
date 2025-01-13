package com.alexius.talktale.presentation.report_card.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.alexius.talktale.ui.theme.Black
import com.alexius.talktale.ui.theme.Blue
import com.alexius.talktale.ui.theme.Poppins
import com.alexius.talktale.R
import com.alexius.talktale.presentation.common.MainButton
import com.alexius.talktale.presentation.home_screen.HomeScreen
import com.alexius.talktale.ui.theme.TalkTaleTheme

@Composable
fun EndReportCardScreen(
    modifier: Modifier = Modifier,
    storiesCompleted: Int,
    onEndButtonClick: () -> Unit
) {
    Column(
        modifier = modifier.fillMaxSize().padding(horizontal = 20.dp).statusBarsPadding()
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Report Card",
            style = MaterialTheme.typography.displayMedium,
            color = MaterialTheme.colorScheme.primary,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = modifier.height(47.dp))

        Text(
            text = buildAnnotatedString {
                withStyle(SpanStyle(
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 12.sp,
                    color = Black
                )){
                    append("Selamat! Kamu telah menyelesaikan ")
                }
                withStyle(SpanStyle(
                    fontWeight = FontWeight.Bold,
                    fontSize = 12.sp,
                    color = Blue
                )){
                    append("$storiesCompleted cerita lebih banyak ")
                }
                withStyle(SpanStyle(
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 12.sp,
                    color = Black
                )){
                    append("dibandingkan minggu lalu!")
                }
            },
            fontFamily = Poppins,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = modifier.height(47.dp))

        Image(
            painter = painterResource(id = R.drawable.thanks_girl),
            modifier = modifier.size(274.dp),
            contentScale = ContentScale.Crop,
            contentDescription = null
        )

        Spacer(modifier = modifier.height(47.dp))

        Text(
            text = "Kemajuan Belajar Mingguan",
            style = MaterialTheme.typography.bodySmall,
            fontWeight = FontWeight.SemiBold,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = modifier.height(47.dp))

        Text(
            text = "Teruslah berusaha, dan kamu akan menjadi ahli dalam bahasa Inggris!",
            style = MaterialTheme.typography.labelSmall,
            textAlign = TextAlign.Center
        )
         Spacer(modifier = modifier.height(47.dp))

        MainButton(
            text = "Selesai",
            onClick = onEndButtonClick,
            enabled = true
        )
    }
}

@Preview(showBackground = true, backgroundColor = 0xFFFFFFFF)
@Composable
private fun Prev() {
    TalkTaleTheme {
        EndReportCardScreen(
            storiesCompleted = 5,
            onEndButtonClick = {}
        )
    }
}