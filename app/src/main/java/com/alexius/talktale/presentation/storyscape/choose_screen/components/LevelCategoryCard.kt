package com.alexius.talktale.presentation.storyscape.choose_screen.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.alexius.talktale.R
import com.alexius.talktale.presentation.home_screen.HomeScreen
import com.alexius.talktale.ui.theme.Blue
import com.alexius.talktale.ui.theme.Pink
import com.alexius.talktale.ui.theme.TalkTaleTheme
import com.alexius.talktale.ui.theme.WhitePale
import com.alexius.talktale.ui.theme.Yellow

@Composable
fun LevelCategoryCard(
    modifier: Modifier = Modifier,
    onClickCard: () -> Unit,
    levelCategory: LevelCategory
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .clickable(
                enabled = true,
                onClick = onClickCard
            ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 5.dp
        ),
        colors = CardDefaults.cardColors(
            containerColor = WhitePale
        ),
        shape = MaterialTheme.shapes.extraLarge
    ){
        Row(
            modifier = modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp)
                .padding(vertical = 25.dp)
        ) {
            Image(
                painter = painterResource(id = when(levelCategory) {
                    LevelCategory.BEGINNER -> R.drawable.beginner_level
                    LevelCategory.INTERMEDIATE -> R.drawable.intermediate_level
                    LevelCategory.ADVANCED -> R.drawable.advanced_level
                }),
                modifier = modifier.size(90.dp),
                contentDescription = null
            )

            Spacer(modifier = modifier.width(30.dp))

            Column(
                modifier = modifier.fillMaxWidth()
            ) {
                Text(
                    text = when(levelCategory) {
                        LevelCategory.BEGINNER -> "Beginner"
                        LevelCategory.INTERMEDIATE -> "Intermediate"
                        LevelCategory.ADVANCED -> "Advanced"
                    },
                    style = MaterialTheme.typography.displaySmall.copy(fontWeight = FontWeight.SemiBold),
                    fontSize = 20.sp,
                    color = when(levelCategory) {
                        LevelCategory.BEGINNER -> Blue
                        LevelCategory.INTERMEDIATE -> Yellow
                        LevelCategory.ADVANCED -> Pink
                    }
                )

                Spacer(modifier = modifier.height(10.dp))

                Text(
                    text = buildAnnotatedString {
                        append(when(levelCategory) {
                            LevelCategory.BEGINNER -> "Baca cerita dongeng dengan bahasa inggris level "
                            LevelCategory.INTERMEDIATE -> "Baca cerita dongeng dengan bahasa inggris level "
                            LevelCategory.ADVANCED -> "Baca cerita dongeng dan cerita karir dengan bahasa inggris level "
                        })
                        withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                            append(when(levelCategory) {
                                LevelCategory.BEGINNER -> "beginner"
                                LevelCategory.INTERMEDIATE -> "intermediate"
                                LevelCategory.ADVANCED -> "advanced"
                            })
                        }
                        append(".")
                    },
                    style = MaterialTheme.typography.labelSmall.copy(fontSize = 10.sp)
                )
            }
        }
    }
}

@Preview(showBackground = true, backgroundColor = 0xFFFFFFFF)
@Composable
private fun Prev() {
    TalkTaleTheme {
        LevelCategoryCard(
            onClickCard = {},
            levelCategory = LevelCategory.BEGINNER
        )
    }
}

enum class LevelCategory {
    BEGINNER,
    INTERMEDIATE,
    ADVANCED
}