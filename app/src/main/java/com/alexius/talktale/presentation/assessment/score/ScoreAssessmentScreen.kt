package com.alexius.talktale.presentation.assessment.score

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.alexius.talktale.presentation.assessment.components.ScoreComparisonTable
import com.alexius.talktale.presentation.assessment.components.ScoreInfo
import com.alexius.talktale.presentation.common.CircularProgressBar
import com.alexius.talktale.ui.theme.Blue
import com.alexius.talktale.ui.theme.LightBlue
import com.alexius.talktale.ui.theme.LightOrange
import com.alexius.talktale.ui.theme.LightPink
import com.alexius.talktale.ui.theme.Pink
import com.alexius.talktale.ui.theme.TalkTaleTheme

@Composable
fun ScoreAssessmentScreen(
    modifier: Modifier = Modifier,
    userReadingScore: Int,
    userReadingGrade: String,
    userListeningScore: Int,
    userListeningGrade: String,
    averageScore: Int,
    onMainButtonClick: () -> Unit
) {

    val scrollState = rememberScrollState()

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 24.dp)
            .verticalScroll( enabled = true, state = scrollState),
        horizontalAlignment = Alignment.CenterHorizontally
    )
    {
        Spacer(modifier = modifier.fillMaxWidth().height(74.dp))

        Box(
            modifier = modifier.fillMaxWidth(),
            contentAlignment = Alignment.Center
        ){
            Text(
                text = "Skormu:",
                style = MaterialTheme.typography.displayMedium.copy(fontSize = 28.sp, color = MaterialTheme.colorScheme.primary),
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.Bold
            )
        }

        Spacer(modifier = modifier.fillMaxWidth().height(20.dp))

        CircularProgressBar(
            trackColor = LightOrange,
            progressColor = MaterialTheme.colorScheme.secondary,
            score = averageScore,
            grade = getGrade(averageScore),
            category = getCategory(averageScore),
        )

        Spacer(modifier = modifier.fillMaxWidth().height(20.dp))

        Column(
            modifier = modifier.fillMaxWidth().padding(horizontal = 15.dp)
        ) {
            Text(
                modifier = modifier.fillMaxWidth(),
                text = "Penjelasan",
                style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold),
            )

            Spacer(modifier = modifier.fillMaxWidth().height(20.dp))

            ScoreInfo(
                trackProgressColor = LightBlue,
                progressColor = Blue,
                score = userReadingScore,
                grade = getCategory(userReadingScore),
                category = userReadingGrade,
            )

            Spacer(modifier = modifier.fillMaxWidth().height(20.dp))

            ScoreInfo(
                trackProgressColor = LightPink,
                progressColor = Pink,
                score = userListeningScore,
                grade = getCategory(userListeningScore),
                category = userListeningGrade,
            )
        }

        Spacer(modifier = modifier.fillMaxWidth().height(20.dp))

        ScoreComparisonTable(
            modifier = modifier.fillMaxWidth(),
            userCategory = getCategory(userReadingScore),
        )

        Spacer(modifier = modifier.fillMaxWidth().height(20.dp))

        Button(
            onClick = onMainButtonClick,
            modifier = modifier
                .fillMaxWidth()
                .padding(horizontal = 40.dp)
                .height(44.dp),
            shape = MaterialTheme.shapes.extraLarge
        ){
            Text(
                text = "Lanjut",
                style = MaterialTheme.typography.bodySmall.copy(fontWeight = FontWeight.SemiBold, color = Color.White),
                textAlign = TextAlign.Center
            )
        }

        Spacer(modifier = modifier.fillMaxWidth().height(30.dp))
    }
}

// Helper function to determine category based on score
private fun getCategory(score: Int): String {
    return when (score) {
        in 0..60 -> "Beginner"
        in 61..85 -> "Intermediate"
        in 86..100 -> "Advanced"
        else -> "Invalid Score"
    }
}

private fun getGrade(score: Int): String {
    return when (score) {
        in 0..60 -> "A1/A2"
        in 61..85 -> "B1/B2"
        in 86..100 -> "C1/C2"
        else -> "Invalid Score"
    }
}

// Preview
@Preview(showBackground = true, backgroundColor = 0xFFFFFFFF)
@Composable
private fun Prev() {
    TalkTaleTheme {
        ScoreAssessmentScreen(
            userReadingScore = 80,
            userListeningScore = 80,
            averageScore = 85,
            userReadingGrade = "B1/B2",
            userListeningGrade = "B2/C1",
            onMainButtonClick = {}
        )
    }
}