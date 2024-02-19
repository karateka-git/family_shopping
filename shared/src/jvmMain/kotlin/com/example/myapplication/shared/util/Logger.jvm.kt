package com.example.myapplication.shared.util

import java.util.logging.Level
import java.util.logging.Logger

private val logger = Logger.getLogger("JVM Logger")

actual fun Log.Companion.d(tag: String, message: Any) {
    logger.log(Level.INFO, message.toString())
}
