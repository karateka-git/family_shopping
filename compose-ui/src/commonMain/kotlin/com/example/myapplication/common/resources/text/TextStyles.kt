package com.example.myapplication.common.resources.text

import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import com.example.myapplication.common.resources.Colors

@Composable
fun TextStyleMedium14(
    textAlign: TextAlign? = null,
    color: Color = Colors.Black,
) = TextStyle(
    textAlign = textAlign,
    color = color,
    fontFamily = LatoFontFamily(),
    fontStyle = FontStyle.Normal,
    fontWeight = FontWeight.Normal,
    fontSize = 14.sp,
    lineHeight = 16.sp,
)

@Composable
fun TextStyleBold14(
    textAlign: TextAlign? = null,
    color: Color = Colors.Black,
) = TextStyle(
    textAlign = textAlign,
    color = color,
    fontFamily = LatoFontFamily(),
    fontStyle = FontStyle.Normal,
    fontWeight = FontWeight.Bold,
    fontSize = 14.sp,
    lineHeight = 16.sp,
)