package com.alexius.talktale.presentation.storyscape.choose_screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.alexius.core.R
import com.alexius.talktale.presentation.common.StoryCard
import com.alexius.talktale.presentation.common.TopBarQuiz
import com.alexius.talktale.ui.theme.Blue
import com.alexius.talktale.ui.theme.Orange
import com.alexius.talktale.ui.theme.Pink

@Composable
fun CareerChooseScreen(
    modifier: Modifier = Modifier,
    onClickFirstPlay: () -> Unit,
    onClickSecondPlay: () -> Unit,
    onClickThirdPlay: () -> Unit,
    onBackClick: () -> Unit,
    listCompleted: List<Boolean>
) {
    val scrollState = rememberScrollState()

    Scaffold(
        modifier = modifier.fillMaxSize().systemBarsPadding(),
        topBar = {
            TopBarQuiz(
                title = "StoryScape",
                onBackClick = onBackClick
            )
        }
    ) {innerPadding ->
        Column(
            modifier = modifier.fillMaxSize().padding(horizontal = 20.dp)
                .padding(innerPadding)
                .verticalScroll(scrollState)
        ) {

            Spacer(modifier = modifier.fillMaxWidth().height(10.dp))

            Text(
                text = "Pilih cerita seru dan belajar bahasa Inggris sesuai levelmu!",
                style = MaterialTheme.typography.labelSmall,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = modifier.fillMaxWidth().height(20.dp))

            Text(
                text = "Kesehatan",
                style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold),
                color = Orange
            )

            Spacer(modifier = modifier.fillMaxWidth().height(10.dp))

            StoryCard(
                imageDrawable = R.drawable.no_smoking,
                title = "The Journey of Smoking",
                description = "The Transformative Path of Quitting Smoking",
                completed = if (listCompleted.isNotEmpty()) listCompleted[0] else false,
                locked = false,
                onClickPlay = onClickFirstPlay
            )

            Spacer(modifier = modifier.fillMaxWidth().height(10.dp))

            Text(
                text = "Teknik",
                style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold),
                color = Orange
            )

            Spacer(modifier = modifier.fillMaxWidth().height(10.dp))

            StoryCard(
                imageDrawable = R.drawable.engineering,
                title = "How Hydroelectric Dams Generate Power",
                description = "The Mechanics and Impact of Hydroelectric Dams",
                completed = if (listCompleted.isNotEmpty()) listCompleted[1] else false,
                locked = false,
                onClickPlay = onClickSecondPlay
            )

            Spacer(modifier = modifier.fillMaxWidth().height(10.dp))

            Text(
                text = "Ekonomi",
                style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold),
                color = Orange
            )

            Spacer(modifier = modifier.fillMaxWidth().height(10.dp))

            StoryCard(
                imageDrawable = com.alexius.core.R.drawable.money,
                title = "The Impact of Inflation on Prices and the Economy",
                description = "How Rising Prices Affect Your Wallet and the Economy",
                completed = if (listCompleted.isNotEmpty()) listCompleted[2] else false,
                locked = false,
                onClickPlay = onClickThirdPlay
            )

        }
    }
}