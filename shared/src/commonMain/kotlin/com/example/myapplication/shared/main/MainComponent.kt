package com.example.myapplication.shared.main

import com.arkivanov.decompose.value.Value
import com.example.myapplication.shared.models.ShoppingItem

interface MainComponent {
    val state: Value<StateUI>

    fun updateFABVisibleState(isVisible: Boolean)
    fun createNewItem()
    fun onAddItem(text: String)
    fun onRemoveItem(id: String)
    fun onChangeChecked(id: String)
    fun onEditItem(id: String)

    data class StateUI(
        val items: List<ShoppingItem>,
        val isVisibleFAB: Boolean = true,
        val newItemText: String,
    )
}
