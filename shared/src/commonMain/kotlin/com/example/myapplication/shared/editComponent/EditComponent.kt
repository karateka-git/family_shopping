package com.example.myapplication.shared.editComponent

import com.arkivanov.decompose.value.Value
import com.arkivanov.essenty.parcelable.Parcelable
import com.arkivanov.essenty.parcelable.Parcelize
import com.example.myapplication.shared.models.ShoppingItem

interface EditComponent {

    val state: Value<EditState>

    fun onBackClicked()
    fun onUpdateItem(text: String)
    fun onApplyEdit()

    data class EditState(
        // TODO можно сделать Exception, но надо подумать как обработать
        val error: String? = null,
        val editMode: EditMode = EditMode.CreateNew,
        val item: ShoppingItem = ShoppingItem(),
    )
}

@Parcelize
sealed interface EditMode: Parcelable {
    data object CreateNew: EditMode
    class Edit(val itemId: String, val isChecked: Boolean) : EditMode
}
