package com.behnamuix.colordetect.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDirection
import androidx.compose.ui.unit.sp

// Set of Material typography styles to start with
val Typography = Typography(
    bodyLarge = TextStyle(
        fontFamily = Vazirfont,
        textDirection = TextDirection.Rtl,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.5.sp
    ),
    labelLarge = TextStyle(
        fontFamily = Vazirfont,
        textDirection = TextDirection.Ltr
    ),
    titleMedium = TextStyle(
        fontWeight = FontWeight.Bold,
        fontFamily = Vazirfont,
        textDirection = TextDirection.Ltr
    ),
    bodyMedium = TextStyle(
        fontFamily = Vazirfont,
        textDirection = TextDirection.Rtl
    ),
    displayLarge = TextStyle(
        fontSize = 26.sp,
        fontFamily = Vazirfont,
        textDirection = TextDirection.Rtl
    ),
    /* Other default text styles to override
    titleLarge = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 22.sp,
        lineHeight = 28.sp,
        letterSpacing = 0.sp
    ),
    labelSmall = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Medium,
        fontSize = 11.sp,
        lineHeight = 16.sp,
        letterSpacing = 0.5.sp
    )
    */
)