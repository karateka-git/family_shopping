package com.example.myapplication.common.resources.text

import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.platform.Typeface
import org.jetbrains.skia.FontStyle
import org.jetbrains.skia.Typeface

private fun loadCustomFont(name: String): Typeface {
    return Typeface.makeFromName(name, FontStyle.NORMAL)
}

// может и не сработать, проверить тут
// https://jassielcastro.medium.com/custom-fonts-in-android-and-ios-applications-using-kotlin-multiplatform-and-jetpack-compose-c88d2d519e77
actual fun LatoFontFamily(): FontFamily = FontFamily(
    Typeface(loadCustomFont("lato_regular"))
)