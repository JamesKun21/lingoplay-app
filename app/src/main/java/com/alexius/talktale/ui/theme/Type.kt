package com.alexius.talktale.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Typography
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.alexius.talktale.R

val Poppins = FontFamily(
    fonts = listOf(
        Font(R.font.poppins_regular, FontWeight.Normal),
        Font(R.font.poppins_bold, FontWeight.Bold),
        Font(R.font.poppins_semibold, FontWeight.SemiBold),
    )
)

// Set of Material typography styles to start with
val Typography = Typography(
    displaySmall = TextStyle(
        fontSize = 24.sp,
        fontFamily = Poppins,
        fontWeight = FontWeight.Normal,
        color = Black,
        lineHeight = 36.sp,
    ),
    displayMedium = TextStyle(
        fontSize = 32.sp,
        fontFamily = Poppins,
        fontWeight = FontWeight.Normal,
        color = Black,
        lineHeight = 48.sp,
    ),
    bodySmall = TextStyle(
        fontSize = 14.sp,
        fontFamily = Poppins,
        fontWeight = FontWeight.Normal,
        color = Black,
        lineHeight = 21.sp,
    ),
    bodyMedium = TextStyle(
        fontSize = 16.sp,
        fontFamily = Poppins,
        fontWeight = FontWeight.Normal,
        color = Black,
        lineHeight = 24.sp,
    ),
    labelSmall = TextStyle(
        fontSize = 12.sp,
        fontFamily = Poppins,
        fontWeight = FontWeight.Normal,
        color = Black,
        lineHeight = 19.sp,
    ),
)