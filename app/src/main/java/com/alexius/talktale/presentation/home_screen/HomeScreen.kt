package com.alexius.talktale.presentation.home_screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.alexius.talktale.presentation.common.LoadingScreen
import com.alexius.talktale.presentation.common.shimmerEffect
import com.alexius.talktale.ui.theme.LightGreen
import com.alexius.talktale.ui.theme.TalkTaleTheme
import com.alexius.talktale.R
import com.alexius.talktale.presentation.common.StoryCard
import com.alexius.talktale.presentation.home_screen.components.ProgressCard
import com.alexius.talktale.ui.theme.Poppins
import com.alexius.talktale.ui.theme.White
import com.alexius.talktale.ui.theme.WhitePale

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    userName: String,
    category: String,
    onClickStoryCard: () -> Unit,
    onClickReportCard: () -> Unit
) {

    val scrollState = rememberScrollState()

    Surface(
        modifier = modifier.fillMaxSize(),
        color = White
    ) {
        Column(
            modifier = modifier
                .fillMaxSize()
                .verticalScroll( enabled = true, state = scrollState)
                .padding(horizontal = 20.dp)
        ) {
            Spacer(modifier = modifier.fillMaxWidth().height(64.dp))

            Card(
                modifier = modifier
                    .fillMaxWidth(),
                elevation = CardDefaults.cardElevation(
                    defaultElevation = 5.dp
                ),
                colors = CardDefaults.cardColors(
                    containerColor = WhitePale
                ),
                shape = MaterialTheme.shapes.extraLarge
            ) {
                Row(
                    modifier = modifier
                        .fillMaxSize()
                        .padding(20.dp)
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.momoji),
                        modifier = modifier
                            .size(70.dp),
                        contentDescription = null
                    )

                    Spacer(modifier = modifier.width(20.dp))

                    Column(
                        modifier = modifier
                            .fillMaxSize()
                            .padding(vertical = 10.dp)
                    ) {
                        Row(
                            modifier = modifier.fillMaxWidth()
                        ) {
                            Text(
                                text = "Halo, ",
                                style = MaterialTheme.typography.bodySmall
                            )

                            Text(
                                text = "$userName!",
                                style = MaterialTheme.typography.bodySmall.copy(fontWeight = FontWeight.Bold)
                            )
                        }

                        Spacer(modifier = modifier.height(10.dp))

                        Row(
                            modifier = modifier.fillMaxWidth()
                        ) {
                            Text(
                                text = "Level kamu : ",
                                style = MaterialTheme.typography.labelSmall.copy(color = MaterialTheme.colorScheme.primary)
                            )

                            Text(
                                text = category,
                                style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.SemiBold, color = MaterialTheme.colorScheme.primary)
                            )
                        }
                    }
                }
            }

            Spacer(modifier = modifier.height(10.dp))

            Column(
                modifier = modifier.fillMaxWidth()
            ) {
                Text(
                    text = "Belum Kamu Baca",
                    style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold)
                )

                Spacer(modifier = modifier.height(5.dp))

                Text(
                    text = "Baca cerita yang belum kamu baca",
                    style = MaterialTheme.typography.labelSmall
                )
            }

            Spacer(modifier = modifier.height(10.dp))

            StoryCard(
                imageDrawable = R.drawable.timun_mas,
                title = "Timun Mas",
                description = "The Golden Cucumber Adventure",
                completed = false,
                locked = false,
                onClickPlay = onClickStoryCard
            )

            Spacer(modifier = modifier.height(10.dp))
/*
            Column(
                modifier = modifier.fillMaxWidth()
            ) {
                Text(
                    text = "Sudah Kamu Baca",
                    style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold)
                )

                Spacer(modifier = modifier.height(5.dp))

                Text(
                    text = "Lihat kembali cerita yang sudah kamu baca",
                    style = MaterialTheme.typography.labelSmall
                )
            }

            Spacer(modifier = modifier.height(10.dp))*/

            StoryCard(
                imageDrawable = R.drawable.lion,
                title = "The Lion and the Mouse",
                description = "The Unexpected Friendship Adventure",
                completed = false,
                locked = false,
                onClickPlay = onClickStoryCard
            )

            Spacer(modifier = modifier.height(10.dp))

            StoryCard(
                imageDrawable = com.alexius.core.R.drawable.red_hood,
                title = "The Girl in the Red Hood",
                description = "The Little Red Riding Hood Adventure",
                completed = false,
                locked = false,
                onClickPlay = onClickStoryCard
            )

            Spacer(modifier = modifier.height(10.dp))

            Column(
                modifier = modifier.fillMaxWidth()
            ) {
                Text(
                    text = "Report Card Terbaru",
                    style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold)
                )

                Spacer(modifier = modifier.height(5.dp))

                Text(
                    text = "Lihat progress belajar kamu selama beberapa hari ini",
                    style = MaterialTheme.typography.labelSmall
                )
            }

            Spacer(modifier = modifier.height(10.dp))

            ProgressCard(
                onClickNext = onClickReportCard
            )

        }
    }
}

@Preview(showBackground = true, backgroundColor = 0xFFFFFFFF)
@Composable
private fun Prev() {
    TalkTaleTheme {
        HomeScreen(
            userName = "Alexius",
            category = "Pemula",
            onClickStoryCard = {},
            onClickReportCard = {}
        )
    }
}