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
import androidx.compose.ui.unit.dp
import com.alexius.talktale.presentation.common.TopBarQuiz
import com.alexius.talktale.presentation.storyscape.choose_screen.components.AdvancedCategoryCard
import com.alexius.talktale.ui.theme.Blue
import com.alexius.talktale.ui.theme.Orange
import com.alexius.talktale.ui.theme.Pink
import com.alexius.core.R
import com.alexius.talktale.ui.theme.Yellow

@Composable
fun StoryCareerChooseScreen(
    modifier: Modifier = Modifier,
    onClickFirstPlay: () -> Unit,
    onClickSecondPlay: () -> Unit,
    onBackClick: () -> Unit,
) {

    Scaffold(
        modifier = modifier.fillMaxSize().systemBarsPadding(),
        topBar = {
            TopBarQuiz(
                title = "StoryScape",
                onBackClick = onBackClick
            )
        }
    ){ innerPadding ->
        Column(
            modifier = modifier.fillMaxSize().padding(horizontal = 20.dp)
                .padding(innerPadding)
                .verticalScroll(rememberScrollState())
        ){
            Spacer(modifier = modifier.fillMaxWidth().height(20.dp))

            Text(
                text = "Advanced",
                style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold),
                color = Orange
            )

            Spacer(modifier = modifier.fillMaxWidth().height(20.dp))

            AdvancedCategoryCard(
                onClickCard = onClickFirstPlay,
                imageRes = R.drawable.tales,
                titleColor = Blue,
                title = "Cerita Dongeng",
                category = "dongeng"
            )

            Spacer(modifier = modifier.fillMaxWidth().height(20.dp))

            AdvancedCategoryCard(
                onClickCard = onClickSecondPlay,
                imageRes = R.drawable.money,
                titleColor = Yellow,
                title = "Cerita Karir",
                category = "karir"
            )
        }
    }
}