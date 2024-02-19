package com.example.myapplication.shared.root

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.router.stack.StackNavigation
import com.arkivanov.decompose.router.stack.childStack
import com.arkivanov.decompose.router.stack.pop
import com.arkivanov.decompose.router.stack.push
import com.arkivanov.decompose.value.Value
import com.arkivanov.essenty.parcelable.Parcelable
import com.arkivanov.essenty.parcelable.Parcelize
import com.example.myapplication.shared.main.DefaultMainComponent
import com.example.myapplication.shared.main.MainComponent
import com.example.myapplication.shared.root.RootComponent.Child
import com.example.myapplication.shared.editComponent.DefaultEditComponent
import com.example.myapplication.shared.editComponent.EditComponent

class DefaultRootComponent(
    componentContext: ComponentContext,
) : RootComponent, ComponentContext by componentContext {

    private val navigation = StackNavigation<Config>()

    override val stack: Value<ChildStack<*, Child>> =
        childStack(
            source = navigation,
            initialConfiguration = Config.Main,
            handleBackButton = true,
            childFactory = ::child,
        )

    private fun child(config: Config, childComponentContext: ComponentContext): Child =
        when (config) {
            is Config.Main -> Child.Main(mainComponent(childComponentContext))
            is Config.EditItem -> Child.EditItem(editComponent(childComponentContext, config.id))
        }

    private fun mainComponent(componentContext: ComponentContext): MainComponent =
        DefaultMainComponent(
            componentContext = componentContext,
            openEditComponent = { id -> navigation.push(Config.EditItem(id)) },
        )

    private fun editComponent(componentContext: ComponentContext, itemId: String): EditComponent =
        DefaultEditComponent(
            componentContext = componentContext,
            itemId = itemId,
            onFinished = ::navigateToBack,
        )

    override fun navigateToBack() {
        navigation.pop()
    }

    private sealed interface Config : Parcelable {
        @Parcelize
        data object Main : Config
        @Parcelize
        data class EditItem(val id: String) : Config
    }
}
