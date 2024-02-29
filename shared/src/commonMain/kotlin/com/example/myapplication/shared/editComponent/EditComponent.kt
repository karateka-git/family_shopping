package com.example.myapplication.shared.editComponent

import com.arkivanov.decompose.value.Value
import com.example.myapplication.shared.models.ShoppingItem

interface EditComponent {

    val state: Value<EditState>

    fun onBackClicked()
    fun onUpdateItem(text: String)
    fun onUpdateItem()
    fun onCreateNewItem()

    data class EditState(
        // TODO можно сделать Exception, но надо подумать как обработать
        val error: String? = null,
        val editMode: EditMode = EditMode.CREATE_NEW,
        val item: ShoppingItem = ShoppingItem(),
    )
}

enum class EditMode {
    CREATE_NEW, EDIT
}
