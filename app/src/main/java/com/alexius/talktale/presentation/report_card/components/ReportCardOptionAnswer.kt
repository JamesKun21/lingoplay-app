package com.alexius.talktale.presentation.report_card.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.alexius.talktale.ui.theme.Grey
import com.alexius.talktale.ui.theme.LightOrange
import com.alexius.talktale.ui.theme.Orange

@Composable
fun ReportCardOptionAnswer(
    modifier: Modifier = Modifier,
    onAnswerSelected: (Int) -> Unit,
    options: List<String>,
    selectedAnswerIndex: MutableState<Int>
) {
    Column {
        options.forEachIndexed { index, answer ->
            OutlinedButton(
                onClick = {
                    onAnswerSelected(index)
                    selectedAnswerIndex.value = index
                },
                modifier = modifier.heightIn(min = 60.dp).width(240.dp),
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

            if (index < options.size - 1) {
                Spacer(modifier = Modifier.padding(8.dp))
            }
        }
    }
}