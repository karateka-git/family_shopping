package com.example.myapplication.shared.main

import com.arkivanov.decompose.router.slot.ChildSlot
import com.arkivanov.decompose.value.Value
import com.example.myapplication.shared.editComponent.EditComponent
import com.example.myapplication.shared.main.store.MainStore
import com.example.myapplication.shared.models.ShoppingItem
import kotlinx.coroutines.flow.StateFlow

interface MainComponent {
    val bottomStack: Value<ChildSlot<*, BottomChild>>
    val state: StateFlow<MainStore.State>

    fun updateFABVisibleState(isVisible: Boolean)
    fun onRemoveItem(id: String)
    fun updateItem(item: ShoppingItem)
    fun onEditItem(id: String, isChecked: Boolean)
    fun onCreateNewItem()

    data class MainState(
        val items: List<ShoppingItem> = emptyList(),
        val isVisibleFAB: Boolean = true,
    )

    sealed class BottomChild {
        class BottomEditItem(val component: EditComponent) : BottomChild()
    }
}
