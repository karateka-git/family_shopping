package com.example.myapplication.shared.main.store

import com.arkivanov.mvikotlin.core.store.Reducer
import com.arkivanov.mvikotlin.core.store.SimpleBootstrapper
import com.arkivanov.mvikotlin.core.store.Store
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.extensions.coroutines.CoroutineExecutor
import com.example.myapplication.shared.editComponent.EditMode
import com.example.myapplication.shared.main.MainInteractor
import com.example.myapplication.shared.main.store.MainStore.Intent
import com.example.myapplication.shared.main.store.MainStore.Label
import com.example.myapplication.shared.main.store.MainStore.State
import com.example.myapplication.shared.models.ShoppingItem
import com.example.myapplication.shared.util.appDispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

internal class MainStoreProvider(
    private val storeFactory: StoreFactory,
): KoinComponent {

    private val mainInteractor: MainInteractor by inject<MainInteractor>()

    fun provide(): MainStore =
        object : MainStore, Store<Intent, State, Label> by storeFactory.create(
            name = "TodoListStore",
            initialState = State(),
            bootstrapper = SimpleBootstrapper(Action.Init),
            executorFactory = ::ExecutorImpl,
            reducer = ReducerImpl
        ) {}

    private sealed interface Action {
        data object Init : Action
        data class UpdateItem(val item: ShoppingItem) : Action
    }

    private sealed interface Message {
        data class UpdateItems(val items: List<ShoppingItem>) : Message
        data class AddItem(val newItem: ShoppingItem) : Message
        data class SetFabVisibleState(val isVisible: Boolean) : Message
    }

    private inner class ExecutorImpl : CoroutineExecutor<Intent, Action, State, Message, Label>(
        appDispatchers.main
    ) {
        override fun executeAction(action: Action, getState: () -> State) {
            when(action) {
                is Action.Init -> loadItems()
                is Action.UpdateItem -> {
                    val updatedItem = action.item
                    getState().items.updateItem(updatedItem.id) {
                        copy(
                            text = updatedItem.text,
                            isChecked = updatedItem.isChecked
                        )
                    }
                }
            }
        }

        private var updateItemJob: Job? = null
        private fun List<ShoppingItem>.updateItem(id: String, func: ShoppingItem.() -> ShoppingItem) {
            if (updateItemJob?.isActive == true) updateItemJob?.cancel()

            updateItemJob = scope.launch(appDispatchers.default) {
                // TODO добавить обработку
                val item = find { it.id == id } ?: throw Exception("TODO")
                val updatedItem = item.func()

                if (item == updatedItem) return@launch

                withContext(appDispatchers.io) {
                    delay(500)
                    mainInteractor.updateItem(updatedItem)
                }
                val updatedItems = associateByTo(mutableMapOf(), ShoppingItem::id).apply {
                    set(item.id, updatedItem)
                }.values.toList()

                withContext(appDispatchers.main) {
                    dispatch(Message.UpdateItems(updatedItems))
                }
            }
        }

        override fun executeIntent(intent: Intent, getState: () -> State) {
            when(intent) {
                is Intent.UpdateFABVisibleState -> dispatch(Message.SetFabVisibleState(intent.isVisible))
                is Intent.UpdateItem -> executeAction(Action.UpdateItem(intent.item))
                is Intent.AddItem -> dispatch(Message.AddItem(intent.newItem))
                is Intent.RemoveItem -> TODO()
                is Intent.EditItem -> publish(Label.OpenEditView(mode = EditMode.Edit(intent.id, intent.isChecked)))
                is Intent.CreateNewItem -> publish(Label.OpenEditView(mode = EditMode.CreateNew))
            }
        }

        private fun loadItems() {
            scope.launch {
                val items = mainInteractor.getItems()
                dispatch(Message.UpdateItems(items))
            }
        }
    }

    private object ReducerImpl : Reducer<State, Message> {
        override fun State.reduce(msg: Message): State =
            when(msg) {
                is Message.UpdateItems -> copy(items = msg.items)
                is Message.AddItem -> copy(items = items + msg.newItem)
                is Message.SetFabVisibleState -> copy(isVisibleFAB = msg.isVisible)
            }
    }
}
