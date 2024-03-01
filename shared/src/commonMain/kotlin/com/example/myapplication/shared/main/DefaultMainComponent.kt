package com.example.myapplication.shared.main

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.slot.ChildSlot
import com.arkivanov.decompose.router.slot.SlotNavigation
import com.arkivanov.decompose.router.slot.activate
import com.arkivanov.decompose.router.slot.childSlot
import com.arkivanov.decompose.router.slot.dismiss
import com.arkivanov.decompose.value.MutableValue
import com.arkivanov.decompose.value.Value
import com.arkivanov.decompose.value.update
import com.arkivanov.essenty.parcelable.Parcelable
import com.arkivanov.essenty.parcelable.Parcelize
import com.example.myapplication.shared.editComponent.DefaultEditComponent
import com.example.myapplication.shared.editComponent.EditComponent
import com.example.myapplication.shared.editComponent.EditMode
import com.example.myapplication.shared.main.MainComponent.MainState
import com.example.myapplication.shared.models.ShoppingItem
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class DefaultMainComponent(
    componentContext: ComponentContext,
) : MainComponent, ComponentContext by componentContext, KoinComponent {

    private val mainInteractor: MainInteractor by inject<MainInteractor>()

    private val bottomNavigation = SlotNavigation<BottomConfig>()

    override val bottomStack: Value<ChildSlot<*, MainComponent.BottomChild>> =
        childSlot(
            source = bottomNavigation,
            key = "BottomSlot",
            childFactory = ::bottomChild
        )
    private val _state = MutableValue(MainState())
    override val state: MutableValue<MainState> = _state

    init {
        _state.update { it.copy(items = mainInteractor.getItems()) }
    }

    override fun updateFABVisibleState(isVisible: Boolean) {
        state.update { it.copy(isVisibleFAB = isVisible) }
    }

    override fun onRemoveItem(id: String) {
        TODO("Not yet implemented")
    }

    override fun onChangeChecked(id: String) {
        mainInteractor.onChangeCheckedState(id)

        val items = mutableListOf<ShoppingItem>().apply { addAll(_state.value.items) }
        val index = items.indexOfFirst { it.id == id }
        if (index > -1) {
            val item = items.get(index)
            items.set(
                index = index,
                item.copy(isChecked = item.isChecked.not())
            )
        }
        _state.update { it.copy(items = items) }
    }

    override fun openEditView(mode: EditMode) {
        bottomNavigation.activate(BottomConfig.BottomEditItemConfig(mode))
    }

    private fun bottomChild(config: BottomConfig, bottomComponentContext: ComponentContext): MainComponent.BottomChild =
        when (config) {
            is BottomConfig.BottomEditItemConfig ->
                MainComponent.BottomChild.BottomEditItem(editComponent(bottomComponentContext, config.mode))
        }

    private fun editComponent(componentContext: ComponentContext, mode: EditMode): EditComponent =
        DefaultEditComponent(
            componentContext = componentContext,
            mode = mode,
            onApplyEditItem = { item ->
                val items = mutableListOf<ShoppingItem>().apply { addAll(_state.value.items) }
                when (mode) {
                    is EditMode.CreateNew -> {
                        _state.update { it.copy(items = items.apply { add(item) }) }
                    }
                    is EditMode.Edit -> {
                        val index = items.indexOfFirst { it.id == item.id }
                        if (index > -1) {
                            _state.update { it.copy(items = items.apply { set(index, item) }) }
                        }
                    }
                }
            },
            onFinished = {
                bottomNavigation.dismiss()
            },
        )

    private sealed interface BottomConfig : Parcelable {
        @Parcelize
        data class BottomEditItemConfig(val mode: EditMode) : BottomConfig
    }
}
