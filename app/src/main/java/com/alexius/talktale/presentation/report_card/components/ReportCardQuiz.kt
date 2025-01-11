package com.alexius.talktale.presentation.report_card.components

import androidx.compose.foundation.Image
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
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.alexius.talktale.presentation.common.MainButton
import com.alexius.talktale.presentation.common.TopBarQuiz

@Composable
fun ReportCardQuiz(
    modifier: Modifier = Modifier,
    onBackClick: () -> Unit,
    imageRes: Int,
    question: String,
    answers: List<String>,
    onAnswerSelected: (Int) -> Unit,
    selectedAnswerIndex: MutableState<Int>,
    onNextClick: () -> Unit
) {
    Scaffold(
        modifier = modifier
            .fillMaxSize()
            .systemBarsPadding(),
        topBar = {
            TopBarQuiz(
                title = "Rewind Questions",
                onBackClick = onBackClick
            )
        },

    ) { innerPadding ->
        Column(
            modifier = modifier
                .verticalScroll(rememberScrollState())
                .padding(innerPadding)
                .padding(horizontal = 37.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ){
            Image(
                painter = painterResource(id = imageRes),
                contentDescription = null,
                modifier = modifier.fillMaxWidth().height(180.dp)
                    .clip(MaterialTheme.shapes.large),
                contentScale = ContentScale.Crop
            )

            Spacer(modifier = modifier.height(30.dp))

            Text(
                text = question,
                style = MaterialTheme.typography.labelSmall,
                fontWeight = FontWeight.SemiBold,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = modifier.height(30.dp))

            ReportCardOptionAnswer(
                options = answers,
                onAnswerSelected = onAnswerSelected,
                selectedAnswerIndex = selectedAnswerIndex
            )

            Spacer(modifier = modifier.height(30.dp))

            MainButton(
                text = "Lanjut",
                onClick = onNextClick,
                enabled = selectedAnswerIndex.value != -1
            )
        }
    }
}