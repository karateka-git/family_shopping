package com.example.myapplication.shared.root

import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.value.Value
import com.example.myapplication.shared.main.MainComponent

interface RootComponent {

    val stack: Value<ChildStack<*, Child>>

    fun navigateToBack()

    sealed class Child {
        class Main(val component: MainComponent) : Child()
    }
}
