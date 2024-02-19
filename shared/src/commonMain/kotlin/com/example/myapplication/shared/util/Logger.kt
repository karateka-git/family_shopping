package com.example.myapplication.shared.util

interface Log {
    companion object
}

expect fun Log.Companion.d(tag: String, message: Any)
