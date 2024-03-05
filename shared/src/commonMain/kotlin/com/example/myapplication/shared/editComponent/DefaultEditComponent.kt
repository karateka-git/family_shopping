package com.example.myapplication.shared.editComponent

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.value.MutableValue
import com.arkivanov.decompose.value.Value
import com.arkivanov.decompose.value.update
import com.example.myapplication.shared.editComponent.EditComponent.EditState
import com.example.myapplication.shared.main.MainInteractor
import com.example.myapplication.shared.models.ShoppingItem
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class DefaultEditComponent(
    private val componentContext: ComponentContext,
    private val mode: EditMode,
    private val onApplyEditItem: (item: ShoppingItem) -> Unit,
    private val onFinished: () -> Unit,
) : EditComponent, ComponentContext by componentContext, KoinComponent {

    private val mainInteractor: MainInteractor by inject<MainInteractor>()

    // Consider preserving and managing the state via a store
    // для стейтов надо бы сторы создавать получая их так:
    // private val store = instanceKeeper.getStore { ChatRootStore(storeFactory) }
    private val _state = MutableValue(EditState())
    override val state: Value<EditState> = _state

    init {
        when(mode) {
            is EditMode.CreateNew ->
                _state.update { it.copy(item = ShoppingItem(), editMode = mode) }
            is EditMode.Edit -> {
                val item = mainInteractor.getItem(mode.itemId)

                if (item == null) {
                    _state.update { it.copy(error = "Что-то пошло не так..", editMode = mode) }
                } else {
                    _state.update { it.copy(item = item.copy(isChecked = mode.isChecked), editMode = mode) }
                }
            }
        }
    }

    override fun onUpdateItem(text: String) {
        _state.update { it.copy(item = it.item.copy(text = text)) }
    }

    override fun onApplyEdit() {
        val currentItem = _state.value.item
        val appliedItem = when (_state.value.editMode) {
            is EditMode.Edit -> {
                mainInteractor.updateItem(currentItem)
                currentItem
            }
            is EditMode.CreateNew -> mainInteractor.createNewItem(currentItem)
        }
        onApplyEditItem(appliedItem)
        onBackClicked()
    }
    override fun onBackClicked() {
        onFinished()
    }
}
