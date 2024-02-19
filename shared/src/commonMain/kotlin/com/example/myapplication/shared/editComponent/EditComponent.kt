package com.example.myapplication.shared.editComponent

import com.arkivanov.decompose.value.Value

interface EditComponent {

    val model: Value<Model>

    fun onUpdateGreetingText()
    fun onBackClicked()

    data class Model(
        val greetingText: String = "Welcome from Decompose!"
    )
}
