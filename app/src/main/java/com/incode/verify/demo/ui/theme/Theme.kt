package com.incode.verify.demo.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Typography
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import com.incode.verify.demo.R

object IncodeFontFamily {
    val dm_sans = FontFamily(
        listOf(
            Font(R.font.dm_sans_regular, weight = FontWeight.Normal),
            Font(R.font.dm_sans_medium, weight = FontWeight.Medium),
            Font(R.font.dm_sans_semi_bold, weight = FontWeight.SemiBold),
            Font(R.font.dm_sans_bold, weight = FontWeight.Bold),
        )
    )
    val work_sans = FontFamily(
        listOf(
            Font(R.font.work_sans_regular, weight = FontWeight.Normal),
            Font(R.font.work_sans_medium, weight = FontWeight.Medium),
            Font(R.font.work_sans_bold, weight = FontWeight.Bold)
        )
    )

}

object IncodeColors {
    val Purple = Color(0xFF8531FF)
    val Purple10 = Color(0x1A8531FF)
    val Black = Color(0xFF000000)
    val Black70 = Color(0xB2000000)
    val Blue = Color(0xFF007AFF)
    val White = Color(0xFFFFFFFF)
    val Gray1 = Color(0xFF221D1A)
    val Gray2 = Color(0xFF404040)
    val Gray3 = Color(0xFF656565)
}

val IncodeTypography = Typography(
    headlineLarge = TextStyle(
        fontSize = 32.sp,
        lineHeight = 31.sp,
        fontFamily = IncodeFontFamily.work_sans,
        fontWeight = FontWeight.SemiBold,
        color = IncodeColors.Black,
        textAlign = TextAlign.Center
    ), headlineMedium = TextStyle(
        fontSize = 24.sp,
        lineHeight = 31.sp,
        fontFamily = IncodeFontFamily.dm_sans,
        fontWeight = FontWeight.SemiBold,
        color = IncodeColors.White
    ), headlineSmall = TextStyle(
        fontSize = 16.sp,
        lineHeight = 21.sp,
        fontFamily = IncodeFontFamily.dm_sans,
        fontWeight = FontWeight.Medium,
        color = IncodeColors.White
    ), bodyMedium = TextStyle(
        fontSize = 14.sp,
        lineHeight = 18.2.sp,
        fontFamily = IncodeFontFamily.dm_sans,
        fontWeight = FontWeight.Medium,
        color = IncodeColors.Gray1
    ), displayMedium = TextStyle(
        fontSize = 12.sp,
        fontFamily = IncodeFontFamily.dm_sans,
        fontWeight = FontWeight.Medium,
        color = IncodeColors.Gray2,
        textAlign = TextAlign.Center
    ), displayLarge = TextStyle(
        fontSize = 24.sp,
        lineHeight = 31.2.sp,
        fontFamily = IncodeFontFamily.dm_sans,
        fontWeight = FontWeight.Bold,
        color = IncodeColors.Black,
        textAlign = TextAlign.Center
    ), labelMedium = TextStyle(
        fontSize = 18.sp,
        fontFamily = IncodeFontFamily.dm_sans,
        fontWeight = FontWeight.SemiBold,
        color = IncodeColors.White,
        textAlign = TextAlign.Center
    ), labelSmall = TextStyle(
        fontSize = 12.sp,
        fontFamily = IncodeFontFamily.dm_sans,
        fontWeight = FontWeight.Medium,
        color = IncodeColors.Gray2,
        textAlign = TextAlign.Center
    )
)

val Typography.headlineSmallAlt: TextStyle
    get() = TextStyle(
        fontSize = 16.sp,
        fontFamily = IncodeFontFamily.work_sans,
        fontWeight = FontWeight.Medium,
        color = IncodeColors.Black70,
        textAlign = TextAlign.Center
    )

val Typography.bodyMediumAlt: TextStyle
    get() = TextStyle(
        fontSize = 14.sp,
        fontFamily = IncodeFontFamily.work_sans,
        fontWeight = FontWeight.Medium,
        color = IncodeColors.Black70
    )
private val LightColorScheme = lightColorScheme(
    primary = IncodeColors.Purple, background = Color.White
)

@Composable
fun IncodeVerifyAndroidTheme(
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = LightColorScheme, typography = IncodeTypography, content = content
    )
}
