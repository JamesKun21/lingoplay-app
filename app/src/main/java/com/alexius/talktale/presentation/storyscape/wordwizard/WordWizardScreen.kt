package com.alexius.talktale.presentation.storyscape.wordwizard

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowBackIosNew
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.alexius.core.domain.model.GrammarResponse
import com.alexius.core.domain.model.VocabularyResponse
import com.alexius.talktale.presentation.assessment.components.HintText
import com.alexius.talktale.presentation.home_screen.HomeScreen
import com.alexius.talktale.ui.theme.GreenAnswer
import com.alexius.talktale.ui.theme.LightOrange
import com.alexius.talktale.ui.theme.Orange
import com.alexius.talktale.ui.theme.RedError
import com.alexius.talktale.ui.theme.TalkTaleTheme
import com.alexius.talktale.ui.theme.WhitePale

@OptIn(ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class)
@Composable
fun WordWizardScreen(
    modifier: Modifier = Modifier,
    title: String,
    subtitle: String,
    imageDrawable: Int,
    grammarResponse: GrammarResponse,
    vocabularyResponse: VocabularyResponse,
    onEndButton: () -> Unit
) {

    val scrollState = rememberScrollState()

    Scaffold(
        modifier = modifier.fillMaxSize().statusBarsPadding(),
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "StoryScape",
                        style = MaterialTheme.typography.displaySmall,
                        fontSize = 20.sp,
                        textAlign = TextAlign.Center,
                        color = MaterialTheme.colorScheme.primary
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.Transparent
                )
            )
        }
    ){
        Column(
            modifier.fillMaxSize().padding(it).padding(horizontal = 40.dp).verticalScroll(scrollState),
            horizontalAlignment = Alignment.CenterHorizontally
        ){
            Row(
                modifier = modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Box(
                    modifier = modifier.height(53.dp).width(146.dp).clip(MaterialTheme.shapes.large).background(
                        LightOrange
                    ),
                    contentAlignment = Alignment.Center,
                ){
                    Text(
                        text = "WordWizard",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.secondary
                    )
                }

                Column {
                    Text(
                        text = title,
                        style = MaterialTheme.typography.bodyMedium,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = modifier.height(7.dp))
                    Text(
                        text = subtitle,
                        style = MaterialTheme.typography.labelSmall,
                        fontWeight = FontWeight.SemiBold
                    )
                }
            }

            Spacer(modifier = modifier.height(20.dp))

            Image(
                painter = painterResource(id = imageDrawable),
                contentDescription = null,
                modifier = modifier.height(229.dp).width(258.dp)
                    .clip(MaterialTheme.shapes.large),
                contentScale = ContentScale.Crop
            )

            Spacer(modifier = modifier.height(20.dp))

            Column(
                modifier = modifier.fillMaxWidth(),
            ) {
                Text(
                    text = "Grammar",
                    style = MaterialTheme.typography.labelSmall,
                    fontWeight = FontWeight.SemiBold
                )

                Spacer(modifier = modifier.height(10.dp))

                Text(
                    text = grammarResponse.incorrectSentence,
                    style = MaterialTheme.typography.labelSmall,
                    fontWeight = FontWeight.SemiBold,
                    color = RedError
                )

                Spacer(modifier = modifier.height(10.dp))

                grammarResponse.corrections.forEach {correction ->
                    HintText(
                        text = correction,
                    )
                }

                Spacer(modifier = modifier.height(10.dp))

                Text(
                    text = grammarResponse.correctedSentence,
                    color = GreenAnswer,
                    style = MaterialTheme.typography.labelSmall,
                    fontWeight = FontWeight.SemiBold
                )
            }

            Spacer(modifier = modifier.height(20.dp))

            Column(
                modifier = modifier.fillMaxWidth(),
            ){
                Text(
                    text = "Vocabulary",
                    style = MaterialTheme.typography.labelSmall,
                    fontWeight = FontWeight.SemiBold
                )

                Spacer(modifier = modifier.height(10.dp))

                Text(
                    text = vocabularyResponse.originalSentence,
                    style = MaterialTheme.typography.labelSmall,
                    fontWeight = FontWeight.SemiBold,
                    color = RedError
                )

                Spacer(modifier = modifier.height(10.dp))

                vocabularyResponse.alternativeWords.forEach { (word, alternatives) ->
                    Text(
                        text = "Alternatif kata \"$word\":",
                        style = MaterialTheme.typography.labelSmall,
                    )

                    Spacer(modifier = modifier.height(10.dp))

                    FlowRow(
                        modifier = modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        alternatives.forEach { alternative ->
                            Box(
                                modifier = modifier.clip(MaterialTheme.shapes.small).background(
                                    Orange
                                ),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = alternative,
                                    style = MaterialTheme.typography.labelSmall,
                                    color = WhitePale,
                                )
                            }
                        }
                    }
                }
            }

            Spacer(modifier = modifier.height(31.dp))

            Button(
                onClick = onEndButton,
                modifier = modifier
                    .fillMaxWidth()
                    .height(44.dp),
                shape = MaterialTheme.shapes.extraLarge
            ){
                Text(
                    text = "Selesai",
                    style = MaterialTheme.typography.bodySmall.copy(fontWeight = FontWeight.SemiBold, color = Color.White),
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
        WordWizardScreen(
            title = "The Lion and The Mouse",
            subtitle = "Aesop's Fables",
            imageDrawable = 0,
            grammarResponse = GrammarResponse(
                incorrectSentence = "The lion and the mouse is a story.",
                corrections = listOf("The lion and the mouse are a story."),
                correctedSentence = "The lion and the mouse are a story."
            ),
            vocabularyResponse = VocabularyResponse(
                originalSentence = "The lion and the mouse is a story.",
                alternativeWords = mapOf(
                    "lion" to listOf("tiger", "leopard"),
                    "mouse" to listOf("rat", "hamster")
                )
            ),
            onEndButton = {}
        )
    }
}