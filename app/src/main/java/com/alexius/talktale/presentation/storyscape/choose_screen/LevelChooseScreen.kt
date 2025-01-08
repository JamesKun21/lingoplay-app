package com.alexius.talktale.presentation.storyscape.choose_screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.alexius.talktale.presentation.home_screen.HomeScreen
import com.alexius.talktale.presentation.storyscape.choose_screen.components.LevelCategory
import com.alexius.talktale.presentation.storyscape.choose_screen.components.LevelCategoryCard
import com.alexius.talktale.ui.theme.TalkTaleTheme
import com.alexius.talktale.ui.theme.White

@Composable
fun LevelChooseScreen(
    modifier: Modifier = Modifier,
    onBeginnerLevelChosen: () -> Unit,
    onIntermediateLevelChosen: () -> Unit,
    onAdvancedLevelChosen: () -> Unit
) {
    val scrollState = rememberScrollState()

    Surface(
        modifier = modifier.fillMaxSize(),
        color = White
    ){
        Column(
            modifier = modifier
                .fillMaxSize()
                .verticalScroll( enabled = true, state = scrollState)
                .padding(horizontal = 20.dp)
        ){
            Spacer(modifier = modifier.fillMaxWidth().height(73.dp))

            Text(
                text = "StoryScape",
                style = MaterialTheme.typography.displayMedium.copy(fontSize = 28.sp, fontWeight = FontWeight.Bold),
                color = MaterialTheme.colorScheme.primary,
                textAlign = TextAlign.Center
            )

            Text(
                text = "Pilih cerita seru dan belajar bahasa Inggris sesuai levelmu!",
                style = MaterialTheme.typography.labelSmall,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = modifier.fillMaxWidth().height(25.dp))

            Text(
                text = "Kamu bisa baca cerita berdasarkan level pilihanmu.",
                style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.SemiBold),
            )

            Spacer(modifier = modifier.fillMaxWidth().height(20.dp))

            LevelCategoryCard(
                onClickCard = onBeginnerLevelChosen,
                levelCategory = LevelCategory.BEGINNER
            )

            Spacer(modifier = modifier.fillMaxWidth().height(20.dp))

            LevelCategoryCard(
                onClickCard = onIntermediateLevelChosen,
                levelCategory = LevelCategory.INTERMEDIATE
            )

            Spacer(modifier = modifier.fillMaxWidth().height(20.dp))

            LevelCategoryCard(
                onClickCard = onAdvancedLevelChosen,
                levelCategory = LevelCategory.ADVANCED
            )
        }
    }
}

@Preview(showBackground = true, backgroundColor = 0xFFFFFFFF)
@Composable
private fun Prev() {
    TalkTaleTheme {
        LevelChooseScreen(
            onBeginnerLevelChosen = {},
            onIntermediateLevelChosen = {},
            onAdvancedLevelChosen = {}
        )
    }
}