package com.example.myapplication.shared.editComponent

import com.arkivanov.decompose.value.MutableValue
import com.arkivanov.decompose.value.Value
import com.example.myapplication.shared.models.ShoppingItem

interface EditComponent {

    val state: Value<EditState>
    fun onBackClicked()

    data class EditState(
        // TODO можно сделать Exception, но надо подумать как обработать
        val error: String? = null,
        val item: ShoppingItem = ShoppingItem(),
    )
}
