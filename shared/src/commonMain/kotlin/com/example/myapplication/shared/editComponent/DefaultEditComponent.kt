package com.example.myapplication.shared.editComponent

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.mvikotlin.core.binder.BinderLifecycleMode
import com.arkivanov.mvikotlin.core.instancekeeper.getStore
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.extensions.coroutines.bind
import com.arkivanov.mvikotlin.extensions.coroutines.labels
import com.arkivanov.mvikotlin.extensions.coroutines.stateFlow
import com.example.myapplication.shared.editComponent.store.EditStore
import com.example.myapplication.shared.editComponent.store.EditStoreProvider
import com.example.myapplication.shared.models.ShoppingItem
import com.example.myapplication.shared.util.appDispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.StateFlow
import org.koin.core.component.KoinComponent

class DefaultEditComponent(
    componentContext: ComponentContext,
    storeFactory: StoreFactory,
    private val mode: EditMode,
    private val onApplyEditItem: (item: ShoppingItem) -> Unit,
    private val onFinished: () -> Unit,
) : EditComponent, ComponentContext by componentContext, KoinComponent {

    private val editStore =
        instanceKeeper.getStore {
            EditStoreProvider(
                storeFactory = storeFactory,
                mode = mode,
            ).provide()
        }

    @OptIn(ExperimentalCoroutinesApi::class)
    override val state: StateFlow<EditStore.State> = editStore.stateFlow

    init {
        bind(componentContext.lifecycle, BinderLifecycleMode.CREATE_DESTROY, appDispatchers.main) {
            editStore.labels.bindTo {
                when(it) {
                    is EditStore.Label.FinishWithResult -> {
                        onApplyEditItem(it.item)
                        navigateToBack()
                    }
                }
            }
        }
    }

    override fun onUpdateText(text: String) {
        editStore.accept(EditStore.Intent.UpdateText(text))
    }

    override fun onUpdateIsChecked(isChecked: Boolean) {
        editStore.accept(EditStore.Intent.UpdateIsChecked(isChecked))
    }


    override fun onApplyEdit() {
        editStore.accept(EditStore.Intent.ApplyEditItem)
    }
    override fun navigateToBack() {
        onFinished()
    }
}
