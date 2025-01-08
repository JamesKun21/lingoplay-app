package com.alexius.talktale.presentation.storyscape.choose_screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.alexius.talktale.R
import com.alexius.talktale.presentation.common.StoryCard
import com.alexius.talktale.ui.theme.Blue
import com.alexius.talktale.ui.theme.Pink

@Composable
fun StoryChooseScreen(
    modifier: Modifier = Modifier,
    onClickPlay: () -> Unit,
    category: String,
) {
    val scrollState = rememberScrollState()

    Column(
        modifier = modifier.fillMaxSize().padding(horizontal = 20.dp)
            .verticalScroll(scrollState)
    ) {
        Spacer(modifier = modifier.fillMaxWidth().height(73.dp))

        Text(
            text = "StoryScape",
            style = MaterialTheme.typography.displayMedium.copy(fontSize = 28.sp, fontWeight = FontWeight.Bold),
            color = MaterialTheme.colorScheme.primary,
            textAlign = TextAlign.Center
        )

        Text(
            text = "Pilih cerita seru dan belajar bahasa Inggris sesuai levelmu!",
            style = MaterialTheme.typography.labelSmall,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = modifier.fillMaxWidth().height(20.dp))

        Text(
            text = category,
            style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold),
            color = when(category){
                "Beginner" -> MaterialTheme.colorScheme.secondary
                "Intermediate" -> Blue
                else -> Pink
            }
        )

        Spacer(modifier = modifier.fillMaxWidth().height(10.dp))

        StoryCard(
            imageDrawable = R.drawable.timun_mas,
            title = "Timun Mas",
            description = "The Golden Cucumber Adventure",
            completed = false,
            locked = false,
            onClickPlay = onClickPlay
        )

        Spacer(modifier = modifier.fillMaxWidth().height(10.dp))

        StoryCard(
            imageDrawable = R.drawable.lion,
            title = "The Lion and The Mouse",
            description = "The Unexpected Friendship Adventure",
            completed = false,
            locked = true,
            onClickPlay = {}
        )

        Spacer(modifier = modifier.fillMaxWidth().height(10.dp))

        StoryCard(
            imageDrawable = com.alexius.core.R.drawable.red_hood,
            title = "The Girl in the Red Hood",
            description = "The Little Red Riding Hood Adventure",
            completed = false,
            locked = true,
            onClickPlay = {}
        )

    }
}