package com.alexius.talktale.presentation.common

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.alexius.talktale.R
import com.alexius.talktale.presentation.home_screen.HomeScreen
import com.alexius.talktale.ui.theme.Grey
import com.alexius.talktale.ui.theme.TalkTaleTheme
import com.alexius.talktale.ui.theme.WhitePale

@Composable
fun StoryCard(
    modifier: Modifier = Modifier,
    imageDrawable: Int,
    title: String,
    description: String,
    completed: Boolean,
    locked: Boolean,
    onClickPlay: () -> Unit
) {
    Card(
        modifier = modifier
            .fillMaxWidth(),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 5.dp
        ),
        colors = CardDefaults.cardColors(
            containerColor = if (locked) Grey else WhitePale
        ),
        shape = MaterialTheme.shapes.extraLarge
    ){
        Row(
            modifier = modifier
                .padding(vertical = 20.dp)
                .padding(horizontal = 16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(id = imageDrawable),
                modifier = modifier
                    .size(70.dp)
                    .clip(CircleShape),
                contentDescription = null,
                contentScale = ContentScale.Crop
            )

            Spacer(modifier = modifier.width(12.dp))

            Column(
                modifier = modifier
                    .fillMaxWidth()
                    .padding(vertical = 10.dp)
                    .weight(1f)
            ) {
                Text(
                    text = title,
                    style = MaterialTheme.typography.bodySmall.copy(fontWeight = FontWeight.SemiBold)
                )

                Spacer(modifier = modifier.height(4.dp))

                Text(
                    text = description,
                    style = MaterialTheme.typography.labelSmall
                )
            }

            Image(
                painter = painterResource(
                    id = if (completed){
                        R.drawable.complete_story
                    } else if (!completed && locked){
                        R.drawable.locked
                    } else{
                        R.drawable.play_story
                    }
                ),
                modifier = modifier
                    .size(50.dp)
                    .clip(CircleShape)
                    .clickable(
                        enabled = !locked,
                        onClick = onClickPlay
                    ),
                contentDescription = null
            )
        }
    }
}

@Preview(showBackground = true, backgroundColor = 0xFFFFFFFF)
@Composable
private fun Prev() {
    TalkTaleTheme {
        StoryCard(
            imageDrawable = R.drawable.lion,
            title = "The Lion and the Mouse",
            description = "The Unexpected Friendship Adventure",
            completed = true,
            locked = false,
            onClickPlay = {}
        )
    }
}