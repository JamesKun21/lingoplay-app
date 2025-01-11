package com.alexius.talktale.presentation.report_card.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.alexius.core.domain.model.ReportCardAnalysis
import com.alexius.talktale.presentation.assessment.components.HintText
import com.alexius.talktale.presentation.common.MainButton
import com.alexius.talktale.presentation.common.TopBarQuiz
import com.alexius.talktale.ui.theme.Black
import com.alexius.talktale.ui.theme.Blue
import com.alexius.talktale.ui.theme.GreenAnswer
import com.alexius.talktale.ui.theme.Grey
import com.alexius.talktale.ui.theme.LightBlue
import com.alexius.talktale.ui.theme.LightPink
import com.alexius.talktale.ui.theme.Pink
import com.alexius.talktale.ui.theme.RedError

@Composable
fun ReportCardWordWizard(
    modifier: Modifier = Modifier,
    isGrammar: Boolean,
    analysisList: List<ReportCardAnalysis>,
    onBackClick: () -> Unit,
    onNextClick: () -> Unit
) {
    Scaffold(
        modifier = modifier.fillMaxSize().systemBarsPadding(),
        topBar = {
            TopBarQuiz(
                title = "Perbaikan WordWizard",
                onBackClick = onBackClick
            )
        }
    ) {innerPadding ->
        Column(
            modifier = modifier.fillMaxWidth().padding(innerPadding).padding(horizontal = 39.dp)
                .verticalScroll(rememberScrollState())
        ) {
            Box(
                modifier = modifier.fillMaxWidth().background(color = if (isGrammar) LightBlue else LightPink).clip(
                    MaterialTheme.shapes.large),
                contentAlignment = Alignment.Center,
            ){
                Text(
                    text = if (isGrammar) "Grammar" else "Vocabulary",
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.labelSmall,
                    fontWeight = FontWeight.SemiBold,
                    color = if (isGrammar) Blue else Pink,
                )
            }

            Spacer(modifier = modifier.height(17.dp))

            Box(
                modifier = modifier.fillMaxWidth()
                    .border(width = 1.dp, color = Grey)
                    .clip(MaterialTheme.shapes.large)
                    .padding(20.dp)
            ){
                analysisList.forEachIndexed { index, analysis ->
                    Text(
                        text = analysis.originalSentence,
                        style = MaterialTheme.typography.labelSmall,
                        fontWeight = FontWeight.SemiBold,
                        color = RedError
                    )
                    Spacer(modifier = modifier.height(10.dp))
                    Text(
                        text = analysis.correctedSentence,
                        style = MaterialTheme.typography.labelSmall,
                        fontWeight = FontWeight.SemiBold,
                        color = GreenAnswer
                    )
                    Spacer(modifier = modifier.height(10.dp))
                    HintText(
                        text = analysis.suggestion
                    )
                    Spacer(modifier = modifier.height(10.dp))
                    if (index < analysisList.size - 1) {
                        HorizontalDivider(
                            modifier = modifier.fillMaxWidth(),
                            color = Black,
                            thickness = 1.dp
                        )
                    }
                }
            }

            Spacer(modifier = modifier.height(55.dp))

            MainButton(
                text = "Lanjut",
                onClick = onNextClick,
                enabled = true
            )
        }
    }
}