package com.alexius.talktale.presentation.storyscape.story_quiz

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.alexius.talktale.ui.theme.Grey
import com.alexius.talktale.ui.theme.LightOrange
import com.alexius.talktale.ui.theme.Orange

@Composable
fun StoryQuizOptionAnswer(
    modifier: Modifier = Modifier,
    onAnswerSelected: (Int) -> Unit,
    answers: List<String>,
    selectedAnswerIndex: MutableState<Int>
) {
    Row {
        answers.forEachIndexed { index, answer ->
            OutlinedButton(
                onClick = {
                    onAnswerSelected(index)
                    selectedAnswerIndex.value = index
                },
                modifier = modifier.size(height = 96.dp, width = 142.dp),
                border = BorderStroke(1.dp, color = if (selectedAnswerIndex.value == index) Orange else Grey),
                shape = MaterialTheme.shapes.small,
                colors = ButtonDefaults.outlinedButtonColors(
                    containerColor = Color.Transparent,
                )
            ) {
                Text(
                    text = answer,
                    style = MaterialTheme.typography.bodySmall,
                    modifier = modifier,
                    textAlign = TextAlign.Center,
                    fontWeight = FontWeight.SemiBold,
                    color = Orange
                )
            }

            if (index < answers.size - 1) {
                Spacer(modifier = modifier.width(15.dp))
            }
        }
    }
}