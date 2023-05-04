package com.aloarte.shopwise.presentation.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.aloarte.shopwise.R


private val tekoMedium = FontFamily(
    Font(R.font.teko_medium, FontWeight.Light)
)

private val tekoLight = FontFamily(
    Font(R.font.teko_light, FontWeight.Light)
)
private val hindMedium = FontFamily(
    Font(R.font.hind_medium, FontWeight.Light)
)


// Set of Material typography styles to start with
val Typography = Typography(
    bodyLarge = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.5.sp
    ),
    titleMedium = TextStyle(
        fontFamily = tekoMedium,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.5.sp
    ),
    headlineSmall = TextStyle(
        fontFamily = hindMedium,
        fontWeight = FontWeight.Medium,
        fontSize = 10.sp,
        lineHeight = 16.sp,
        letterSpacing = 0.3.sp
    ),
    labelSmall = TextStyle(
        fontFamily = tekoLight,
        fontWeight = FontWeight.Medium,
        fontSize = 11.sp,
        lineHeight = 16.sp,
        letterSpacing = 0.5.sp
    ),
    bodyMedium = TextStyle(
        fontFamily = hindMedium,
        fontWeight = FontWeight.Medium,
        fontSize = 11.sp,
        lineHeight = 20.sp,
        letterSpacing = 0.5.sp
    )
)