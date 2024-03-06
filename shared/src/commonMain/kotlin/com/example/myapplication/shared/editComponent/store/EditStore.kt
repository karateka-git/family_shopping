package com.example.myapplication.shared.editComponent.store

import com.arkivanov.mvikotlin.core.store.Store
import com.example.myapplication.shared.editComponent.EditMode
import com.example.myapplication.shared.models.ShoppingItem

interface EditStore : Store<EditStore.Intent, EditStore.State, EditStore.Label> {

    sealed interface Intent {
        data class UpdateText(val text: String) : Intent
        data class UpdateIsChecked(val isChecked: Boolean) : Intent
        data class RemoveItem(val id: String) : Intent
        data object ApplyEditItem : Intent
    }

    sealed interface Label {
        data class FinishWithResult(val item: ShoppingItem) : Label
    }

    data class State(
        val error: Exception? = null,
        val item: ShoppingItem = ShoppingItem(),
        val mode: EditMode = EditMode.CreateNew,
    )
}