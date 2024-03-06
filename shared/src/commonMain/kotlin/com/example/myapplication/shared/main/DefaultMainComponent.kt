package com.example.myapplication.shared.main

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.slot.ChildSlot
import com.arkivanov.decompose.router.slot.SlotNavigation
import com.arkivanov.decompose.router.slot.activate
import com.arkivanov.decompose.router.slot.childSlot
import com.arkivanov.decompose.router.slot.dismiss
import com.arkivanov.decompose.value.Value
import com.arkivanov.essenty.parcelable.Parcelable
import com.arkivanov.essenty.parcelable.Parcelize
import com.arkivanov.mvikotlin.core.binder.BinderLifecycleMode
import com.arkivanov.mvikotlin.core.instancekeeper.getStore
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.extensions.coroutines.bind
import com.arkivanov.mvikotlin.extensions.coroutines.labels
import com.arkivanov.mvikotlin.extensions.coroutines.stateFlow
import com.example.myapplication.shared.editComponent.DefaultEditComponent
import com.example.myapplication.shared.editComponent.EditComponent
import com.example.myapplication.shared.editComponent.EditMode
import com.example.myapplication.shared.main.store.MainStore
import com.example.myapplication.shared.main.store.MainStoreProvider
import com.example.myapplication.shared.models.ShoppingItem
import com.example.myapplication.shared.util.appDispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.StateFlow

class DefaultMainComponent(
    componentContext: ComponentContext,
    private val storeFactory: StoreFactory,
) : MainComponent, ComponentContext by componentContext{

    private val mainStore =
        instanceKeeper.getStore {
            MainStoreProvider(
                storeFactory = storeFactory,
            ).provide()
        }

    init {
        bind(componentContext.lifecycle, BinderLifecycleMode.CREATE_DESTROY, appDispatchers.main) {
            mainStore.labels.bindTo {
                when(it) {
                    is MainStore.Label.OpenEditView -> bottomNavigation.activate(BottomConfig.BottomEditItemConfig(it.mode))
                }
            }
        }
    }

    private val bottomNavigation = SlotNavigation<BottomConfig>()

    override val bottomStack: Value<ChildSlot<*, MainComponent.BottomChild>> =
        childSlot(
            source = bottomNavigation,
            key = "BottomSlot",
            childFactory = ::bottomChild
        )

    @OptIn(ExperimentalCoroutinesApi::class)
    override val state: StateFlow<MainStore.State> = mainStore.stateFlow

    override fun updateFABVisibleState(isVisible: Boolean) {
        mainStore.accept(MainStore.Intent.UpdateFABVisibleState(isVisible))
    }

    override fun onRemoveItem(id: String) {
        TODO("Not yet implemented")
    }

    override fun updateItem(item: ShoppingItem) {
        mainStore.accept(MainStore.Intent.UpdateItem(item))
    }

    override fun onEditItem(id: String) {
        mainStore.accept(MainStore.Intent.EditItem(id))
    }

    override fun onCreateNewItem() {
        mainStore.accept(MainStore.Intent.CreateNewItem)
    }

    private fun bottomChild(config: BottomConfig, bottomComponentContext: ComponentContext): MainComponent.BottomChild =
        when (config) {
            is BottomConfig.BottomEditItemConfig ->
                MainComponent.BottomChild.BottomEditItem(editComponent(bottomComponentContext, config.mode))
        }

    private fun editComponent(componentContext: ComponentContext, mode: EditMode): EditComponent =
        DefaultEditComponent(
            componentContext = componentContext,
            storeFactory = storeFactory,
            mode = mode,
            onApplyEditItem = { item ->
                when (mode) {
                    is EditMode.CreateNew -> {
                        mainStore.accept(MainStore.Intent.AddItem(item))
                    }
                    is EditMode.Edit -> {
                        updateItem(item)
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
