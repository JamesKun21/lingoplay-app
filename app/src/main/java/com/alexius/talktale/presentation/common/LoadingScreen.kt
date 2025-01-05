package com.alexius.talktale.presentation.common

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.alexius.talktale.presentation.navgraph_entry.NavGraphEntry
import com.alexius.talktale.ui.theme.Grey
import com.alexius.talktale.ui.theme.TalkTaleTheme

@Composable
fun LoadingScreen(modifier: Modifier = Modifier, enableLoading: Boolean) {

    if (enableLoading) {
        Box(
            modifier = modifier
                .fillMaxSize()
                .background(color = Grey.copy(alpha = 0.5f),),
            contentAlignment = Alignment.Center
        )
        {
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                CircularProgressIndicator()
                Text(
                    text = "Loading",
                    style = MaterialTheme.typography.bodySmall
                )
            }
        }
    }
}

@Preview(showBackground = true, backgroundColor = 0xFFFFFFFF)
@Composable
private fun LoadingScreenPrev() {
    TalkTaleTheme {
        LoadingScreen(enableLoading = true)
    }
}