package com.alexius.talktale.presentation.storyscape.choose_screen.components

import androidx.compose.foundation.BorderStroke
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.alexius.talktale.R
import com.alexius.talktale.ui.theme.Blue
import com.alexius.talktale.ui.theme.Grey
import com.alexius.talktale.ui.theme.Pink
import com.alexius.talktale.ui.theme.WhitePale
import com.alexius.talktale.ui.theme.Yellow

@Composable
fun AdvancedCategoryCard(
    modifier: Modifier = Modifier,
    onClickCard: () -> Unit,
    imageRes: Int,
    titleColor: Color,
    title: String,
    category: String
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .clickable(
                enabled = true,
                onClick = onClickCard
            ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 0.dp
        ),
        colors = CardDefaults.cardColors(
            containerColor = WhitePale
        ),
        border = BorderStroke(1.dp, color = Grey),
        shape = MaterialTheme.shapes.extraLarge
    ){
        Row(
            modifier = modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp)
                .padding(vertical = 25.dp)
        ) {
            Image(
                painter = painterResource(id = imageRes),
                modifier = modifier.size(90.dp)
                    .clip(CircleShape),
                contentDescription = null,
                contentScale = ContentScale.Crop
            )

            Spacer(modifier = modifier.width(30.dp))

            Column(
                modifier = modifier.fillMaxWidth()
            ) {
                Text(
                    text = title,
                    style = MaterialTheme.typography.displaySmall.copy(fontWeight = FontWeight.SemiBold),
                    fontSize = 20.sp,
                    color = titleColor
                )

                Spacer(modifier = modifier.height(10.dp))

                Text(
                    text = buildAnnotatedString {
                        append("Baca cerita ")
                        append(category)
                        append(" dengan bahasa inggris level ")
                        withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                            append("advanced")
                        }
                        append(".")
                    },
                    style = MaterialTheme.typography.labelSmall.copy(fontSize = 10.sp)
                )
            }
        }
    }
}