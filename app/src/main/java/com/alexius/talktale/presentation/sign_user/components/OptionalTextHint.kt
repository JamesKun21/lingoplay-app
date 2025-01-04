package com.alexius.talktale.presentation.sign_user.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.alexius.talktale.ui.theme.TalkTaleTheme

@Composable
fun OptionalTextHint(
    modifier: Modifier = Modifier,
    title: String,
    hintQuestionText: String,
    hintActionText: String,
    onHintActionClick: () -> Unit
) {
    Column(
        modifier = modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = title,
            modifier = modifier.padding(horizontal = 116.dp),
            style = MaterialTheme.typography.displayMedium.copy(fontWeight = FontWeight.Bold, fontSize = 28.sp, color = MaterialTheme.colorScheme.primary),
            textAlign = TextAlign.Center
        )

        Spacer(modifier = modifier.fillMaxWidth().height(12.dp))

        Row(
            modifier = modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center,
        ){
            Text(
                text = hintQuestionText,
                style = MaterialTheme.typography.labelSmall,
                textAlign = TextAlign.Center
            )
            Text(
                text = hintActionText,
                modifier = modifier.clickable(onClick = {onHintActionClick}),
                style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.primary),
                textAlign = TextAlign.Center
            )
        }
    }
}

@Preview(showBackground = true, backgroundColor = 0xFFFFFFFF)
@Composable
private fun OptionalHintPrev() {
    TalkTaleTheme {
        OptionalTextHint(
            title = "Masuk",
            hintQuestionText = "Belum punya akun?",
            hintActionText = " Daftar",
            onHintActionClick = {}
        )
    }
}
