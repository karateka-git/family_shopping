package com.example.myapplication.shared.editComponent

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.value.MutableValue
import com.arkivanov.decompose.value.Value
import com.arkivanov.decompose.value.update
import com.example.myapplication.shared.editComponent.EditComponent.EditState
import com.example.myapplication.shared.main.MainInteractor
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class DefaultEditComponent(
    private val componentContext: ComponentContext,
    private val itemId: String? = null,
    private val onFinished: () -> Unit,
) : EditComponent, ComponentContext by componentContext, KoinComponent {

    private val mainInteractor: MainInteractor by inject<MainInteractor>()

    // Consider preserving and managing the state via a store
    // для стейтов надо бы сторы создавать получая их так:
    // private val store = instanceKeeper.getStore { ChatRootStore(storeFactory) }
    private val _state = MutableValue(EditState())
    override val state: Value<EditState> = _state

    init {
        if (itemId != null) {
            val item = mainInteractor.getItem(itemId)

            if (item == null) {
                _state.update { it.copy(error = "Что-то пошло не так..", editMode = EditMode.EDIT) }
            } else {
                _state.update { it.copy(item = item, editMode = EditMode.EDIT) }
            }
        }
    }

    override fun onUpdateItem(text: String) {
        _state.update { it.copy(item = it.item.copy(text = text)) }
    }

    override fun onUpdateItem() {
        mainInteractor.updateItem(_state.value.item)
        onBackClicked()
    }

    override fun onCreateNewItem() {
        mainInteractor.createNewItem(_state.value.item)
        onBackClicked()
    }
    override fun onBackClicked() {
        onFinished()
    }
}
