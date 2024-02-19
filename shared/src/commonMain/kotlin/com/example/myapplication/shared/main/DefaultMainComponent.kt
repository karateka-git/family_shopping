package com.example.myapplication.shared.main

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.value.MutableValue
import com.arkivanov.decompose.value.update
import com.example.myapplication.shared.models.ShoppingItem

class DefaultMainComponent(
    componentContext: ComponentContext,
    private val openEditComponent: (id: String) -> Unit,
) : MainComponent, ComponentContext by componentContext {

    override val state: MutableValue<MainComponent.StateUI> =
        MutableValue(
            MainComponent.StateUI(
                items = listOf(
                    ShoppingItem("1", "test", false),
                    ShoppingItem("1", "test", false),
                    ShoppingItem("1", "test", false),
                    ShoppingItem("1", "test", false),
                    ShoppingItem("1", "test", false),
                    ShoppingItem("1", "test", false),
                    ShoppingItem("1", "test", false),
                    ShoppingItem("1", "test", false),
                    ShoppingItem("1", "test", false),
                    ShoppingItem("1", "test", false),
                    ShoppingItem("1", "test", false),
                    ShoppingItem("1", "test", false),
                    ShoppingItem("1", "test", false),
                    ShoppingItem("1", "test", false),
                    ShoppingItem("1", "test", false),
                    ShoppingItem("1", "test", false),
                ),
                newItemText = ""
            )
        )

    override fun updateFABVisibleState(isVisible: Boolean) {
        state.update { it.copy(isVisibleFAB = isVisible) }
    }

    override fun createNewItem() {

    }

    override fun onAddItem(text: String) {
        TODO("Not yet implemented")
    }

    override fun onRemoveItem(id: String) {
        TODO("Not yet implemented")
    }

    override fun onChangeChecked(id: String) {
        TODO("Not yet implemented")
    }

    override fun onEditItem(id: String) {
        openEditComponent(id)
    }
}
