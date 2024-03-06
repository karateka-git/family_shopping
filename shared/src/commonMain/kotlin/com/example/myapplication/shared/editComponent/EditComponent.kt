package com.example.myapplication.shared.editComponent

import com.arkivanov.essenty.parcelable.Parcelable
import com.arkivanov.essenty.parcelable.Parcelize
import com.example.myapplication.shared.editComponent.store.EditStore
import kotlinx.coroutines.flow.StateFlow

interface EditComponent {

    val state: StateFlow<EditStore.State>
    fun navigateToBack()
    fun onUpdateText(text: String)
    fun onUpdateIsChecked(isChecked: Boolean)
    fun onApplyEdit()
}

@Parcelize
sealed interface EditMode: Parcelable {
    data object CreateNew: EditMode
    class Edit(val itemId: String) : EditMode
}
