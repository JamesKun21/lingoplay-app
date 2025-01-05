package com.alexius.talktale.presentation.common

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import com.alexius.talktale.R
import com.alexius.talktale.ui.theme.TalkTaleTheme
import com.alexius.talktale.ui.theme.WhitePale

@Composable
fun LoadingScreen(modifier: Modifier = Modifier, enableLoading: Boolean) {

    val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.cute_overload))
    val progress by animateLottieCompositionAsState(
        composition = composition,
        iterations = LottieConstants.IterateForever,
    )

    if (enableLoading) {
        Box(
            modifier = modifier
                .fillMaxSize()
                .background(color = WhitePale),
            contentAlignment = Alignment.BottomCenter
        )
        {
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Bottom
            ) {
                LottieAnimation(
                    composition = composition,
                    progress = { progress },
                    contentScale = ContentScale.Crop,
                    alignment = Alignment.BottomCenter,
                    modifier = modifier.fillMaxSize()
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