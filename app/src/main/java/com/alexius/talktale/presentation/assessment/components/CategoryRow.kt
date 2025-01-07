package com.alexius.talktale.presentation.assessment.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.alexius.talktale.presentation.navgraph_entry.NavGraphEntry
import com.alexius.talktale.ui.theme.Black
import com.alexius.talktale.ui.theme.LightOrange
import com.alexius.talktale.ui.theme.Orange
import com.alexius.talktale.ui.theme.TalkTaleTheme

@Composable
fun CategoryRow(
    category: String,
    range: String,
    isSelected: Boolean,
    makeTopRounder: Boolean = false,
    makeBotRounder: Boolean = false
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(
                RoundedCornerShape(
                    topStart = if (makeTopRounder) 10.dp else 0.dp,
                    topEnd = if (makeTopRounder) 10.dp else 0.dp,
                    bottomStart = if (makeBotRounder) 10.dp else 0.dp,
                    bottomEnd = if (makeBotRounder) 10.dp else 0.dp
                )
            )
            .height(67.dp)
            .background(
                if (isSelected) MaterialTheme.colorScheme.secondary
                else LightOrange
            ),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier.fillMaxSize().weight(1f),
            contentAlignment = Alignment.Center
        ){
            Text(
                text = category,
                color = Black,
                style = MaterialTheme.typography.bodySmall.copy(
                    fontWeight = FontWeight.SemiBold
                ),
            )
        }

        VerticalDivider(
            modifier = Modifier
                .fillMaxHeight(),
            color = if (isSelected) Color.White else Orange
        )

        Box(
            modifier = Modifier.fillMaxSize().weight(1f),
            contentAlignment = Alignment.Center
        ){
            Text(
                text = range,
                color = Black,
                style = MaterialTheme.typography.bodySmall.copy(
                    fontWeight = FontWeight.SemiBold
                ),
            )
        }
    }
}

@Preview(showBackground = true, backgroundColor = 0xFFFFFFFF)
@Composable
private fun Prev() {
    TalkTaleTheme {
        CategoryRow(
            category = "Category",
            range = "0-100%",
            isSelected = false,
            makeTopRounder = true
        )
    }
}