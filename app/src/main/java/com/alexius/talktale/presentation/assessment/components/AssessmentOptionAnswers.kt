package com.alexius.talktale.presentation.assessment.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.alexius.talktale.ui.theme.Grey
import com.alexius.talktale.ui.theme.LightOrange
import com.alexius.talktale.ui.theme.Orange
import com.alexius.talktale.ui.theme.TalkTaleTheme

@Composable
fun AssessmentOptionAnswer(
    modifier: Modifier = Modifier,
    onAnswerSelected: (Int) -> Unit,
    answers: List<String>,
    selectedAnswerIndex: MutableState<Int>
) {

    Column {
        answers.forEachIndexed { index, answer ->
            OutlinedButton(
                onClick = {
                    onAnswerSelected(index)
                    selectedAnswerIndex.value = index
                },
                modifier = modifier.size(height = 60.dp, width = 240.dp),
                border = BorderStroke(1.dp, color = if (selectedAnswerIndex.value == index) Orange else Grey),
                shape = MaterialTheme.shapes.small,
                colors = ButtonDefaults.outlinedButtonColors(
                    containerColor = if (selectedAnswerIndex.value == index) LightOrange else Color.Transparent,
                )
            ) {
                Text(
                    text = answer,
                    style = MaterialTheme.typography.bodySmall,
                    modifier = modifier,
                    textAlign = TextAlign.Center
                )
            }

            if (index < answers.size - 1) {
                Spacer(modifier = Modifier.padding(8.dp))
            }
        }
    }
}

@Preview(showBackground = true, backgroundColor = 0xFFFFFFFF)
@Composable
private fun OptionAnswersPrev() {
    TalkTaleTheme {

        val selectedAnswerIndex = remember { mutableIntStateOf(-1) }

        AssessmentOptionAnswer(
            onAnswerSelected = {},
            answers = listOf("Answer 1 fhuesrgfusyretguesrbgurefueysrhgusryghsurtghsrtug", "Answer 2", "Answer 3", "Answer 4"),
            selectedAnswerIndex = selectedAnswerIndex

        )
    }
}