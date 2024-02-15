package com.example.myapplication.common.resources.text

import androidx.compose.material.Typography
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

@Composable
fun getTypography(): Typography {
    return Typography(
        h1 = TextStyle(
            fontFamily = LatoFontFamily(),
            fontWeight = FontWeight.Bold,
            fontSize = 52.sp,
        ),
        h2 = TextStyle(
            fontFamily = LatoFontFamily(),
            fontWeight = FontWeight.Bold,
            fontSize = 24.sp,
        ),
        h3 = TextStyle(
            fontFamily = LatoFontFamily(),
            fontWeight = FontWeight.Bold,
            fontSize = 18.sp,
        ),
        h4 = TextStyle(
            fontFamily = LatoFontFamily(),
            fontWeight = FontWeight.Bold,
            fontSize = 16.sp,
        ),
        h5 = TextStyle(
            fontFamily = LatoFontFamily(),
            fontWeight = FontWeight.Bold,
            fontSize = 14.sp,
        ),
        h6 = TextStyle(
            fontFamily = LatoFontFamily(),
            fontWeight = FontWeight.SemiBold,
            fontSize = 12.sp,
        ),
        subtitle1 = TextStyle(
            fontFamily = LatoFontFamily(),
            fontWeight = FontWeight.SemiBold,
            fontSize = 16.sp,
        ),
        subtitle2 = TextStyle(
            fontFamily = LatoFontFamily(),
            fontWeight = FontWeight.Normal,
            fontSize = 14.sp,
        ),
        body1 = TextStyle(
            fontFamily = LatoFontFamily(),
            fontWeight = FontWeight.Normal,
            fontSize = 14.sp,
        ),
        body2 = TextStyle(
            fontFamily = LatoFontFamily(),
            fontWeight = FontWeight.Normal,
            fontSize = 10.sp,
        ),
        button = TextStyle(
            fontFamily = LatoFontFamily(),
            fontWeight = FontWeight.Normal,
            fontSize = 14.sp,
        ),
        caption = TextStyle(
            fontFamily = LatoFontFamily(),
            fontWeight = FontWeight.Normal,
            fontSize = 8.sp,
        ),
        overline = TextStyle(
            fontFamily = LatoFontFamily(),
            fontWeight = FontWeight.Normal,
            fontSize = 12.sp,
        )
    )
}