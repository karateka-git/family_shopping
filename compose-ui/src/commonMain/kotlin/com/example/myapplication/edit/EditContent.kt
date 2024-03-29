package com.example.myapplication.edit

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.Checkbox
import androidx.compose.material.Icon
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SheetState
import androidx.compose.material3.TextField
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.myapplication.common.resources.icons.AppIcons
import com.example.myapplication.common.resources.icons.painter
import com.example.myapplication.common.views.TextMedium14
import com.example.myapplication.shared.editComponent.EditComponent
import com.example.myapplication.shared.editComponent.EditMode
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BottomEditContent(component: EditComponent) {
    val state by component.state.collectAsState()

    val sheetState: SheetState = rememberModalBottomSheetState()
    var expandedProgress by remember { mutableStateOf(0f) }
    LaunchedEffect(Unit) {
        snapshotFlow {
            // Обернул, т.к. несмотря на LaunchedEffect, ломается для descktop'a
            runCatching { sheetState.requireOffset() }.getOrDefault(0f)
        }.map { currentOffset ->
            when {
                currentOffset <= 0 -> 1f
                currentOffset <=50 -> 1 - currentOffset / 50
                else -> 0f
            }
        }.distinctUntilChanged()
        .collect {
            expandedProgress = it
        }
    }
    val scope = rememberCoroutineScope()
    val animateToDismiss: () -> Unit = {
        scope.launch { sheetState.hide() }.invokeOnCompletion {
            if (!sheetState.isVisible) {
                component.navigateToBack()
            }
        }
    }

    val roundedShapeValue by remember(expandedProgress) { mutableStateOf(30 * (1-expandedProgress)) }
    ModalBottomSheet(
        modifier = Modifier.fillMaxSize(),
        onDismissRequest = {
            component.navigateToBack()
        },
        sheetState = sheetState,
        shape = RoundedCornerShape(
            topStart = roundedShapeValue,
            topEnd = roundedShapeValue,
        ),
        dragHandle = null,
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
        ) {
            Box(modifier = Modifier.fillMaxWidth()) {
                Box(
                    modifier = Modifier.padding(vertical = 16.dp).fillMaxWidth().alpha(1-expandedProgress),
                    contentAlignment = Alignment.Center,
                ) {
                    DragHandleView()
                }

                Icon(
                    modifier = Modifier.padding(16.dp).alpha(expandedProgress).clickable { animateToDismiss() },
                    painter = AppIcons.CloseIcon.painter(),
                    contentDescription = null
                )
            }
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
            ) {
                when {
                    state.error != null -> TextMedium14(text = state.error.toString())
                    else -> {
                        Checkbox(checked = state.item.isChecked, onCheckedChange = {
                            component.onUpdateIsChecked(isChecked = it)
                        })
                        TextField(
                            value = state.item.text,
                            onValueChange = { component.onUpdateText(text = it) }
                        )
                        Button(onClick = { component.onApplyEdit() }) {
                            TextMedium14(
                                text = when (state.mode) {
                                    is EditMode.Edit -> "Сохранить"
                                    is EditMode.CreateNew -> "Создать"
                                }
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun DragHandleView() {
    Box(modifier = Modifier.size(width = 44.dp, height = 2.dp).background(Color.Cyan))
}
