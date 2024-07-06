package com.example.wiggle.core.presentation.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.googlefonts.Font
import androidx.compose.ui.text.googlefonts.GoogleFont
import androidx.compose.ui.unit.sp
import com.example.wiggle.core.util.provider

val myfont = FontFamily(
    Font(googleFont = GoogleFont("Quicksand"), fontProvider = provider,weight = FontWeight.Light),
    Font(googleFont = GoogleFont("Quicksand"), fontProvider = provider,weight = FontWeight.Normal),
    Font(googleFont = GoogleFont("Quicksand"), fontProvider = provider,weight = FontWeight.Medium),
    Font(googleFont = GoogleFont("Quicksand"), fontProvider = provider,weight = FontWeight.SemiBold),
    Font(googleFont = GoogleFont("Quicksand"), fontProvider = provider,weight = FontWeight.Bold)
)

val myfont2 = FontFamily(
    Font(googleFont = GoogleFont("Roboto"), fontProvider = provider,weight = FontWeight.Light),
    Font(googleFont = GoogleFont("Roboto"), fontProvider = provider,weight = FontWeight.Normal),
    Font(googleFont = GoogleFont("Roboto"), fontProvider = provider,weight = FontWeight.Medium),
    Font(googleFont = GoogleFont("Roboto"), fontProvider = provider,weight = FontWeight.SemiBold),
    Font(googleFont = GoogleFont("Roboto"), fontProvider = provider,weight = FontWeight.Bold)
)

// Set of Material typography styles to start with
val Typography = Typography(
    bodyMedium = TextStyle(
        fontFamily = myfont2,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        color = TextWhite
    ),
    headlineLarge = TextStyle(
        fontFamily = myfont,
        fontWeight = FontWeight.Bold,
        fontSize = 30.sp,
        color = TextWhite
    ),
    headlineMedium = TextStyle(
        fontFamily = myfont,
        fontWeight = FontWeight.Medium,
        fontSize = 24.sp,
        color = TextWhite
    ),
    bodySmall = TextStyle(
        fontFamily = myfont2,
        fontWeight = FontWeight.Normal,
        fontSize = 12.sp,
        color = TextWhite
    )
)