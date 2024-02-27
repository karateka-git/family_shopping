package com.example.myapplication.common.resources.icons

import androidx.compose.runtime.Composable
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource

enum class AppIcons(val res: String) {
    CloseIcon("drawable/close.xml")
}

@Composable
@OptIn(ExperimentalResourceApi::class)
fun AppIcons.painter() = painterResource(res)
