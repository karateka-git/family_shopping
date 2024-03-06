package com.example.myapplication.shared.editComponent.store

import com.arkivanov.mvikotlin.core.store.Reducer
import com.arkivanov.mvikotlin.core.store.SimpleBootstrapper
import com.arkivanov.mvikotlin.core.store.Store
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.extensions.coroutines.CoroutineExecutor
import com.example.myapplication.shared.editComponent.EditMode
import com.example.myapplication.shared.main.MainInteractor
import com.example.myapplication.shared.models.ShoppingItem
import com.example.myapplication.shared.util.appDispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class EditStoreProvider(
    private val storeFactory: StoreFactory,
    private val mode: EditMode,
): KoinComponent {

    private val mainInteractor: MainInteractor by inject<MainInteractor>()

    fun provide(): EditStore =
        object : EditStore, Store<EditStore.Intent, EditStore.State, EditStore.Label> by storeFactory.create(
            name = "ShoppingEditItemStore",
            initialState = EditStore.State(mode = mode),
            bootstrapper = SimpleBootstrapper(Action.Init),
            executorFactory = ::ExecutorImpl,
            reducer = ReducerImpl
        ) {}

    private sealed interface Action {
        data object Init : Action
    }

    private sealed interface Message {
        data class UpdateItem(val item: ShoppingItem) : Message
        data class UpdateText(val text: String) : Message
        data class UpdateIsChecked(val isChecked: Boolean) : Message
    }

    private inner class ExecutorImpl : CoroutineExecutor<EditStore.Intent, Action, EditStore.State, Message, EditStore.Label>(
        appDispatchers.main
    ) {
        override fun executeAction(action: Action, getState: () -> EditStore.State) {
            when (action) {
                is Action.Init -> loadItem()
            }
        }

        private fun loadItem() {
            when(mode) {
                is EditMode.CreateNew -> dispatch(Message.UpdateItem(ShoppingItem()))
                is EditMode.Edit -> {
                    // TODO добавить обработку ошибки
                    scope.launch {
                        val item = mainInteractor.getItem(mode.itemId)

                        dispatch(Message.UpdateItem(item))
                    }
                }
            }
        }

        override fun executeIntent(intent: EditStore.Intent, getState: () -> EditStore.State) {
            when(intent) {
                is EditStore.Intent.UpdateIsChecked -> dispatch(Message.UpdateIsChecked(intent.isChecked))
                is EditStore.Intent.UpdateText -> dispatch(Message.UpdateText(intent.text))
                is EditStore.Intent.RemoveItem -> TODO()
                is EditStore.Intent.ApplyEditItem -> applyItemChanged(getState().item)
            }
        }

        private fun applyItemChanged(changedItem: ShoppingItem) {
            scope.launch {
                val newItem = withContext(appDispatchers.io) {
                    when (mode) {
                        is EditMode.CreateNew -> mainInteractor.createNewItem(changedItem)
                        is EditMode.Edit -> mainInteractor.updateItem(changedItem)
                    }
                }
                publish(EditStore.Label.FinishWithResult(newItem))
            }
        }
    }

    private object ReducerImpl : Reducer<EditStore.State, Message> {
        override fun EditStore.State.reduce(msg: Message): EditStore.State =
            when(msg) {
                is Message.UpdateItem -> copy(item = msg.item)
                is Message.UpdateIsChecked -> copy(item = item.copy(isChecked = msg.isChecked))
                is Message.UpdateText -> copy(item = item.copy(text = msg.text))
            }
    }
}
