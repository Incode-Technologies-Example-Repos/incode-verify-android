package com.incode.verify.demo.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import com.incode.verify.demo.R

// Set of Material typography styles to start with
val Typography = Typography(
    headlineLarge = TextStyle(
        fontSize = 32.sp,
        lineHeight = 31.sp,
        fontFamily = FontFamily(Font(R.font.dm_sans)),
        fontWeight = FontWeight(600),
        color = Color(0xFF000000)
    ),
    headlineMedium = TextStyle(
        fontFamily = FontFamily(Font(R.font.dm_sans)),
        fontWeight = FontWeight(600),
        fontSize = 24.sp,
        lineHeight = 31.sp,
        letterSpacing = 0.5.sp,
        color = Color.White
    ),
    headlineSmall = TextStyle(
        fontFamily = FontFamily(Font(R.font.dm_sans)),
        fontWeight = FontWeight(500),
        fontSize = 16.sp,
        lineHeight = 21.sp,
        letterSpacing = 0.5.sp,
        color = Color(0xCCFFFFFF)
    ),
    bodyLarge = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.5.sp
    ),
    bodyMedium = TextStyle(
        fontSize = 14.sp,
        lineHeight = 18.sp,
        fontFamily = FontFamily(Font(R.font.dm_sans)),
        fontWeight = FontWeight(800),
        color = Color(0xFF221D1A),
    ),
    bodySmall = TextStyle(
        fontSize = 12.sp,
        fontFamily = FontFamily(Font(R.font.dm_sans)),
        fontWeight = FontWeight(500),
        color = Color(0xFF404040)
    ),
    labelMedium = TextStyle(
        fontSize = 16.sp,
        lineHeight = 20.8.sp,
        fontFamily = FontFamily(Font(R.font.dm_sans)),
        fontWeight = FontWeight(600),
        color = Color(0xFF656565)
    ),
    labelSmall = TextStyle(
        fontSize = 12.sp,
        fontFamily = FontFamily(Font(R.font.dm_sans)),
        fontWeight = FontWeight(500),
        color = Color(0xFF404040)
    )
)