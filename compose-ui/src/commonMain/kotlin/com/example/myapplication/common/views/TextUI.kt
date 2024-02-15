package com.example.myapplication.common.views

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import com.example.myapplication.common.resources.Colors
import com.example.myapplication.common.resources.text.TextStyleBold14
import com.example.myapplication.common.resources.text.TextStyleMedium14

@Composable
fun TextMedium14(
    modifier: Modifier = Modifier,
    text: String,
    textAlign: TextAlign? = null,
    maxLines: Int = Int.MAX_VALUE,
    color: Color = Colors.Black,
) {
    Text(
        modifier = modifier,
        text = text,
        style = TextStyleMedium14(
            textAlign = textAlign,
            color = color,
        ),
        color = color,
        maxLines = maxLines,
    )
}

@Composable
fun TextBold14(
    modifier: Modifier = Modifier,
    text: String,
    textAlign: TextAlign? = null,
    maxLines: Int = Int.MAX_VALUE,
    color: Color = Colors.Black,
) {
    Text(
        modifier = modifier,
        text = text,
        style = TextStyleBold14(
            textAlign = textAlign,
            color = color,
        ),
        color = color,
        maxLines = maxLines,
    )
}