package com.alexius.talktale.presentation.report_card.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.alexius.talktale.R
import com.alexius.talktale.presentation.common.MainButton
import com.alexius.talktale.presentation.home_screen.HomeScreen
import com.alexius.talktale.ui.theme.TalkTaleTheme

@Composable
fun ReportCardBridgeScreen(
    modifier: Modifier = Modifier,
    onNextButton: () -> Unit
) {
    Column(
        modifier = modifier.fillMaxWidth()
            .padding(40.dp)
            .verticalScroll(rememberScrollState())
            .statusBarsPadding(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = modifier.height(27.dp))

        Text(
            text = "Report Card",
            style = MaterialTheme.typography.displayMedium.copy(fontWeight = FontWeight.Bold, fontSize = 28.sp),
            color = MaterialTheme.colorScheme.primary,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = modifier.height(5.dp))

        Text(
            text = "Lihat perkembangan belajar kamu selama beberapa hari ini.",
            style = MaterialTheme.typography.labelSmall,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = modifier.height(54.dp))

        Image(
            painter = painterResource(id = R.drawable.boy_survey),
            modifier = modifier.size(274.dp),
            contentDescription = null,
            contentScale = ContentScale.Crop
        )

        Spacer(modifier = modifier.height(38.dp))

        Text(
            text = "Kemajuan Belajar Mingguan",
            style = MaterialTheme.typography.bodySmall,
            fontWeight = FontWeight.SemiBold,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = modifier.height(12.dp))

        Text(
            text = "Lihat pencapaianmu dan seberapa jauh kamu berkembang!",
            style = MaterialTheme.typography.labelSmall,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = modifier.height(53.dp))

        MainButton(
            modifier = modifier,
            onClick = onNextButton,
            text = "Lanjut",
            enabled = true
        )
    }
}

@Preview(showBackground = true, backgroundColor = 0xFFFFFFFF)
@Composable
private fun Prev() {
    TalkTaleTheme {
        ReportCardBridgeScreen(
            onNextButton = {}
        )
    }
}

