package com.example.myapplication.shared.root

import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.value.Value
import com.example.myapplication.shared.main.MainComponent
import com.example.myapplication.shared.editComponent.EditComponent

interface RootComponent {

    val stack: Value<ChildStack<*, Child>>

    fun navigateToBack()

    sealed class Child {
        class Main(val component: MainComponent) : Child()
        class EditItem(val component: EditComponent) : Child()
    }
}
