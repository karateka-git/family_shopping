package com.example.myapplication.edit

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.arkivanov.decompose.extensions.compose.jetbrains.subscribeAsState
import com.example.myapplication.common.views.TextBold14
import com.example.myapplication.common.views.TextMedium14
import com.example.myapplication.shared.editComponent.EditComponent

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BottomEditContent(component: EditComponent) {
    val model by component.model.subscribeAsState()

    val sheetState = rememberModalBottomSheetState()
    ModalBottomSheet(
        onDismissRequest = {
            component.onBackClicked()
        },
        sheetState = sheetState
    ) {
        Text(model.greetingText)
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
        ) {
            Button(
                onClick = { component.onUpdateGreetingText() },
            ) {
                Text(model.greetingText)
            }
            TextMedium14(text = "Medium 14")
            TextBold14(text = "Bold 14")
        }
    }
}
