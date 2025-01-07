package com.alexius.talktale.presentation.common

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.alexius.talktale.presentation.navgraph_entry.NavGraphEntry
import com.alexius.talktale.ui.theme.LightOrange
import com.alexius.talktale.ui.theme.Orange
import com.alexius.talktale.ui.theme.TalkTaleTheme

@Composable
fun CircularProgressBar(
    modifier: Modifier = Modifier,
    trackColor: Color = LightOrange,
    progressColor: Color = Orange,
    score: Int,
    grade: String,
    category: String
) {

    val animatedScore = remember { Animatable(0f) }

    LaunchedEffect(score) {
        animatedScore.animateTo(
            targetValue = score.toFloat(),
            animationSpec = tween(
                durationMillis = 1000,
                easing = FastOutSlowInEasing
            )
        )
    }
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ){

        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier.size(130.dp)
        ) {
            Canvas(
                modifier = modifier.size(118.dp)
            ) {
                // Background circle
                drawCircle(
                    color = LightOrange,
                    style = Stroke(width = 12.dp.toPx()),
                    radius = (size.minDimension-1.dp.toPx()) / 2
                )

                // Progress arc
                drawArc(
                    color = Orange,
                    startAngle = -90f,
                    sweepAngle = (animatedScore.value * 3.6f),
                    useCenter = false,
                    style = Stroke(
                        width = 12.dp.toPx(),
                        cap = StrokeCap.Round
                    )
                )
            }

            Text(
                text = animatedScore.value.toInt().toString(),
                style = MaterialTheme.typography.displayMedium.copy(
                    fontWeight = FontWeight.SemiBold
                ),
                color = Orange
            )
        }

        Spacer(modifier = modifier.height(16.dp))

        Text(
            text = grade,
            style = MaterialTheme.typography.bodyMedium.copy(
                fontWeight = FontWeight.SemiBold
            ),
            color = Orange
        )

        Spacer(modifier = Modifier.height(4.dp))

        Text(
            text = category,
            style = MaterialTheme.typography.bodyMedium.copy(
                fontWeight = FontWeight.SemiBold
            ),
            color = Orange
        )
    }


}

@Preview(showBackground = true, backgroundColor = 0xFFFFFFFF)
@Composable
private fun CircularProgressPrev() {
    TalkTaleTheme {
        CircularProgressBar(
            score = 85,
            grade = "B1/B2",
            category = "Intermediate"
        )
    }
}
