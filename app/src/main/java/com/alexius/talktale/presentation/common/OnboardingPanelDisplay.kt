package com.alexius.talktale.presentation.common

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.alexius.talktale.R
import com.alexius.talktale.ui.theme.TalkTaleTheme

@Composable
fun OnboardingPanelDisplay(
    modifier: Modifier = Modifier,
    imageDrawable: Int,
    title: String,
    description: String,
    onClickMainButton: () -> Unit,
    mainButtonText: String,
    underButtonContent: @Composable (() -> Unit)? = null,
) {
    Column(
        modifier = modifier
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = modifier
            .fillMaxWidth()
            .height(196.dp))

        Image(
            painter = painterResource(id = imageDrawable),
            contentDescription = null,
            modifier = modifier.size(274.dp),
            contentScale = ContentScale.Crop
        )

        Spacer(modifier = modifier
            .fillMaxWidth()
            .height(38.dp))

        Text(
            text = title,
            modifier = modifier.padding(horizontal = 45.dp),
            style = MaterialTheme.typography.bodySmall.copy(fontWeight = FontWeight.SemiBold),
            textAlign = TextAlign.Center
        )

        Spacer(modifier = modifier
            .fillMaxWidth()
            .height(12.dp))

        Text(
            text = description,
            modifier = modifier.padding(horizontal = 69.dp),
            style = MaterialTheme.typography.bodySmall.copy(fontSize = 12.sp),
            textAlign = TextAlign.Center,
            lineHeight = 18.sp
        )

        Spacer(modifier = modifier
            .fillMaxWidth()
            .height(37.dp))

        Button(
            onClick = onClickMainButton,
            modifier = modifier
                .fillMaxWidth()
                .padding(horizontal = 40.dp)
                .height(44.dp),
            shape = MaterialTheme.shapes.extraLarge
        ){
            Text(
                text = mainButtonText,
                style = MaterialTheme.typography.bodySmall.copy(fontWeight = FontWeight.SemiBold),
                textAlign = TextAlign.Center
            )
        }

        underButtonContent?.let {
            Spacer(modifier = modifier
                .fillMaxWidth()
                .height(10.dp))
            it()
        }
    }
}

@Preview(showBackground = true, backgroundColor = 0xFFFFFFFF)
@Composable
private fun OnboardingPanelDisplayPrev() {
    TalkTaleTheme {
        OnboardingPanelDisplay(
            imageDrawable = R.drawable.girl_reading,
            title = "Pilih Ceritamu, Mulai Petualangan Seru",
            description = "Ambil cerita dari rak ajaib dan belajar bahasa Inggris sambil menikmati kisah seru!",
            onClickMainButton = {},
            mainButtonText = "Lanjut",
            underButtonContent = {
                Text(
                    text = "Lewati",
                    style = MaterialTheme.typography.bodySmall.copy(fontWeight = FontWeight.SemiBold, color = MaterialTheme.colorScheme.primary),
                    textAlign = TextAlign.Center
                )
            }
        )
    }
}