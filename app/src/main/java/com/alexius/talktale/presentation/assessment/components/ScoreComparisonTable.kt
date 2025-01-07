package com.alexius.talktale.presentation.assessment.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.alexius.talktale.ui.theme.TalkTaleTheme

@Composable
fun ScoreComparisonTable(
    userCategory: String,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
    ) {
        Text(
            text = "Perbandingan skor",
            style = MaterialTheme.typography.bodyMedium,
            fontWeight = FontWeight.Bold,
        )

        Spacer(modifier = Modifier.fillMaxWidth().height(20.dp))

        // Categories table
        Column(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            CategoryRow(
                category = "Beginner",
                range = "0 - 60%",
                isSelected = userCategory == "Beginner",
                makeTopRounder = true
            )
            CategoryRow(
                category = "Intermediate",
                range = "61 - 85%",
                isSelected = userCategory == "Intermediate"
            )
            CategoryRow(
                category = "Advanced",
                range = "86 - 100%",
                isSelected = userCategory == "Advanced",
                makeBotRounder = true
            )
        }
    }
}

@Preview(showBackground = true, backgroundColor = 0xFFFFFFFF)
@Composable
private fun Prev() {
    TalkTaleTheme {
        ScoreComparisonTable(userCategory = "Beginner")
    }
}