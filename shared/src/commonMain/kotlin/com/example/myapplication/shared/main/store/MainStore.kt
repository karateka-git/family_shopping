package com.example.myapplication.shared.main.store

import com.arkivanov.mvikotlin.core.store.Store
import com.example.myapplication.shared.editComponent.EditMode
import com.example.myapplication.shared.main.store.MainStore.Intent
import com.example.myapplication.shared.main.store.MainStore.Label
import com.example.myapplication.shared.main.store.MainStore.State
import com.example.myapplication.shared.models.ShoppingItem

interface MainStore : Store<Intent, State, Label> {

    sealed interface Intent {
        data class UpdateFABVisibleState(val isVisible: Boolean) : Intent
        data class RemoveItem(val id: String) : Intent
        data class UpdateItem(val item: ShoppingItem) : Intent
        data class AddItem(val newItem: ShoppingItem) : Intent
        data class EditItem(val id: String, val isChecked: Boolean) : Intent
        data object CreateNewItem : Intent
    }

    sealed interface Label {
        data class OpenEditView(val mode: EditMode) : Label
    }

    data class State(
        val items: List<ShoppingItem> = emptyList(),
        val isVisibleFAB: Boolean = true,
    )
}
