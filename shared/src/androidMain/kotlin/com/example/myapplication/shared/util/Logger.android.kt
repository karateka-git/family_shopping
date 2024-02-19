package com.example.myapplication.shared.util

actual fun Log.Companion.d(tag: String, message: Any) {
    android.util.Log.d(tag, message.toString())
}
