package com.example.myapplication.main

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.Checkbox
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.arkivanov.decompose.extensions.compose.jetbrains.subscribeAsState
import com.example.myapplication.shared.main.MainComponent
import com.example.myapplication.shared.models.ShoppingItem
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map

@Composable
internal fun MainContent(
    component: MainComponent,
    modifier: Modifier = Modifier,
) {
    val state by component.state.subscribeAsState()

    val listState = rememberLazyListState()
    listState.observeScrollingUp(state.isVisibleFAB, component::updateFABVisibleState)

    Scaffold(
        modifier = modifier,
        topBar = {
            TopAppBar(
                title = { Text(text = "Список необходимого") },
            )
        },
        floatingActionButton = {
            ShoppingFloatingActionButton(state.isVisibleFAB, component::createNewItem)
        },
    ) {
        LazyColumn(
            modifier = Modifier.fillMaxSize().padding(it),
            state = listState,
        ) {
            items(state.items) { item ->
                ShoppingItemView(
                    item = item,
                    onItemClick = component::onEditItem,
                )
            }
        }
    }
}

/**
 * custom scroll observe, because nestedScrollConnection doesn't work on desktop
 * for org.jetbrains.compose < 1.6.0
 *
 * after update library, check and change
 * @see <a href="https://github.com/JetBrains/compose-multiplatform/issues/653">issue</a>
 */
@Composable
private fun LazyListState.observeScrollingUp(previousState: Boolean, action: (Boolean) -> Unit) {
    var previousIndex by remember(this) {
        mutableStateOf(
            if (previousState) firstVisibleItemIndex else 0
        )
    }
    var previousScrollOffset by remember(this) {
        mutableStateOf(
            if (previousState) firstVisibleItemScrollOffset else 0
        )
    }

    LaunchedEffect(this) {
        snapshotFlow {
            firstVisibleItemIndex to firstVisibleItemScrollOffset
        }
            .map { (index, offset) ->
                if (previousIndex != index) {
                    previousIndex > index
                } else {
                    previousScrollOffset >= offset
                }.also {
                    previousIndex = index
                    previousScrollOffset = offset
                }
            }
            .distinctUntilChanged()
            .collect {
                action(it)
            }
    }
}

@Composable
fun ShoppingItemView(
    item: ShoppingItem,
    onItemClick: (id: String) -> Unit,
) {
    Row(
        modifier = Modifier.fillMaxWidth().clickable { onItemClick(item.id) }.padding(horizontal = 16.dp),
    ) {
        Text(modifier = Modifier.weight(1f), text = item.text)
        Checkbox(checked = item.isChecked, onCheckedChange = {})
    }
}

@Composable
fun ShoppingFloatingActionButton(isVisible: Boolean, onClick: () -> Unit) {
    AnimatedVisibility(
        visible = isVisible,
        enter = slideInVertically(initialOffsetY = { it * 2 }),
        exit = slideOutVertically(targetOffsetY = { it * 2 }),
    ) {
        FloatingActionButton(
            onClick = onClick,
        ) {
            Icon(Icons.Filled.Add, "")
        }
    }
}
