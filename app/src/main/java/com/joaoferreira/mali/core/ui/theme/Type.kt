package com.joaoferreira.mali.core.ui.theme

import androidx.compose.material.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.joaoferreira.mali.R

private val FontFamily.Companion.Fredoka: FontFamily
    get() = FontFamily(
        Font(R.font.fredoka_bold, FontWeight.Bold),
        Font(R.font.fredoka_semibold, FontWeight.SemiBold),
        Font(R.font.fredoka_medium, FontWeight.Medium),
        Font(R.font.fredoka_regular, FontWeight.Normal),
        Font(R.font.fredoka_light, FontWeight.Light),
    )

val Typography = Typography(
    body1 = TextStyle(
        fontFamily = FontFamily.Fredoka,
        fontWeight = FontWeight.Normal,
        fontSize = 18.sp
    ),
    body2 = TextStyle(
        fontFamily = FontFamily.Fredoka,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp
    ),
    h2 = TextStyle(
        fontFamily = FontFamily.Fredoka,
        fontWeight = FontWeight.SemiBold,
        fontSize = 22.sp,
    ),
    h6 = TextStyle(
        fontFamily = FontFamily.Fredoka,
        fontWeight = FontWeight.Normal,
        fontSize = 10.sp
    ),
    caption = TextStyle(
        fontFamily = FontFamily.Fredoka,
        fontWeight = FontWeight.Medium,
        fontSize = 12.sp,
    )
    /* Other default text styles to override
    button = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.W500,
        fontSize = 14.sp
    ),
    caption = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 12.sp
    )
    */
)