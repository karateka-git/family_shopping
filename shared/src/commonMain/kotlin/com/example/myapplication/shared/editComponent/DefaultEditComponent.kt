package com.example.myapplication.shared.editComponent

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.value.MutableValue
import com.arkivanov.decompose.value.Value
import com.arkivanov.decompose.value.update
import com.example.myapplication.shared.editComponent.EditComponent.EditState

class DefaultEditComponent(
    private val componentContext: ComponentContext,
    private val itemId: String,
    private val onFinished: () -> Unit,
) : EditComponent, ComponentContext by componentContext {

    // Consider preserving and managing the state via a store
    private val _state = MutableValue(EditState())
    override val state: Value<EditState> = _state

    override fun onBackClicked() {
        onFinished()
    }
}
