package com.example.myapplication.shared.main

import com.arkivanov.decompose.router.slot.ChildSlot
import com.arkivanov.decompose.value.Value
import com.example.myapplication.shared.editComponent.EditComponent
import com.example.myapplication.shared.editComponent.EditMode
import com.example.myapplication.shared.models.ShoppingItem

interface MainComponent {
    val bottomStack: Value<ChildSlot<*, BottomChild>>
    val state: Value<MainState>

    fun updateFABVisibleState(isVisible: Boolean)
    fun onRemoveItem(id: String)
    fun onChangeChecked(id: String)
    fun openEditView(mode: EditMode)

    data class MainState(
        val items: List<ShoppingItem> = emptyList(),
        val isVisibleFAB: Boolean = true,
    )

    sealed class BottomChild {
        class BottomEditItem(val component: EditComponent) : BottomChild()
    }
}
