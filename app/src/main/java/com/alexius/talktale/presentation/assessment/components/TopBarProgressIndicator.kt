package com.alexius.talktale.presentation.assessment.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.alexius.talktale.ui.theme.Black
import com.alexius.talktale.ui.theme.Green
import com.alexius.talktale.ui.theme.Grey
import com.alexius.talktale.ui.theme.Orange
import com.alexius.talktale.ui.theme.RedError
import com.alexius.talktale.ui.theme.TalkTaleTheme
import com.alexius.talktale.ui.theme.WhitePale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBarProgressIndicator(
    modifier: Modifier = Modifier,
    currentQuestion: Int,
    totalQuestions: Int,
    onExitClick: () -> Unit,
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 25.dp)
            .height(20.dp) // Standard toolbar height
    ) {

        Row(
            modifier = modifier.fillMaxSize()
        ) {
            // Exit Button
            IconButton(
                onClick = onExitClick,
                modifier = modifier
                    .background(
                        color = RedError,
                        shape = CircleShape
                    )
                    .size(20.dp)
            ) {
                Icon(
                    imageVector = Icons.Filled.Close,
                    contentDescription = "Exit",
                    tint = WhitePale,
                    modifier = modifier.size(8.dp)
                )
            }

            Box(
                modifier = modifier.weight(1f).fillMaxSize(),
                contentAlignment = Alignment.Center
            ){
                // Progress Bar
                LinearProgressIndicator(
                    progress = {currentQuestion.toFloat() / totalQuestions},
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 23.dp)
                        .height(8.dp)
                        .clip(MaterialTheme.shapes.small),
                    color = Orange, // Orange color
                    trackColor = Grey,
                )
            }

            Row(
            ) {
                // Question Counter
                Text(
                    text = "$currentQuestion",
                    style = MaterialTheme.typography.bodySmall,
                    color = Green // Green color
                )

                Text(
                    text = "/$totalQuestions",
                    style = MaterialTheme.typography.bodySmall,
                    color = Black
                )
            }
        }
    }
}

@Preview(showBackground = true, backgroundColor = 0xFFFFFFFF)
@Composable
private fun TopBarProgressPrev() {
    TalkTaleTheme {
        TopBarProgressIndicator(
            currentQuestion = 1,
            totalQuestions = 10,
            onExitClick = { }
        )
    }
}