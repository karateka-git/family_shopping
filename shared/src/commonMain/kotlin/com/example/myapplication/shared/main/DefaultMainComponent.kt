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

    override fun createNewItem() {
        bottomNavigation.activate(BottomConfig.BottomEditItemConfig())
    }

    override fun onRemoveItem(id: String) {
        TODO("Not yet implemented")
    }

    override fun onChangeChecked(id: String) {
        mainInteractor.onChangeCheckedState(id)
    }

    override fun onEditItem(item: ShoppingItem) {
        bottomNavigation.activate(BottomConfig.BottomEditItemConfig(item.id))
    }

    private fun bottomChild(config: BottomConfig, bottomComponentContext: ComponentContext): MainComponent.BottomChild =
        when (config) {
            is BottomConfig.BottomEditItemConfig ->
                MainComponent.BottomChild.BottomEditItem(editComponent(bottomComponentContext, config.itemId))
        }

    private fun editComponent(componentContext: ComponentContext, itemId: String?): EditComponent =
        DefaultEditComponent(
            componentContext = componentContext,
            itemId = itemId,
            onFinished = {
                bottomNavigation.dismiss()
            },
        )

    private sealed interface BottomConfig : Parcelable {
        @Parcelize
        data class BottomEditItemConfig(val itemId: String? = null) : BottomConfig
    }
}
