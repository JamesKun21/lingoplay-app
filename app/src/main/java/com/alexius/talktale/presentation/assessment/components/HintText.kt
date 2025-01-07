package com.alexius.talktale.presentation.assessment.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Circle
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.alexius.talktale.ui.theme.Orange
import com.alexius.talktale.ui.theme.TalkTaleTheme

@Composable
fun HintText(
    modifier: Modifier = Modifier,
    text: String
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = Icons.Filled.Circle,
            contentDescription = null,
            modifier = modifier.size(10.dp),
            tint = Orange
        )

        Spacer(modifier = Modifier.width(15.dp))

        Text(
            text = text,
            modifier = modifier.fillMaxWidth(),
            style = MaterialTheme.typography.labelSmall,
            lineHeight = 14.sp
        )
    }

    Spacer(modifier = modifier.height(5.dp))
}

@Preview(showBackground = true, backgroundColor = 0xFFFFFFFF)
@Composable
private fun HintTextPrev() {
    TalkTaleTheme {
        HintText(
            text = "Pertanyaan di tes ini dapat semakin sulit atau mudah untuk menyesuaikan levelmu." +
                    "Pertanyaan di tes ini dapat semakin sulit atau mudah untuk menyesuaikan levelmu."
        )
    }
}