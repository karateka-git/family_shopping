package com.example.myapplication.shared.editComponent

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.value.MutableValue
import com.arkivanov.decompose.value.Value
import com.arkivanov.decompose.value.update
import com.example.myapplication.shared.editComponent.EditComponent.Model

class DefaultEditComponent(
    private val componentContext: ComponentContext,
    private val itemId: String,
    private val onFinished: () -> Unit,
) : EditComponent, ComponentContext by componentContext {

    // Consider preserving and managing the state via a store
    private val state = MutableValue(Model())
    override val model: Value<Model> = state

    override fun onUpdateGreetingText() {
        state.update { it.copy(greetingText = "Edit item with id $itemId") }
    }

    override fun onBackClicked() {
        onFinished()
    }
}
