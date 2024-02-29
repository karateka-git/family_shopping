package com.example.myapplication.shared.main

import com.example.myapplication.shared.models.ShoppingItem

class MainInteractor {
    private val mockItems = mutableListOf(
        ShoppingItem("1", "один", false),
        ShoppingItem("2", "два", false),
        ShoppingItem("3", "три", false),
        ShoppingItem("4", "четыре", false),
        ShoppingItem("5", "пять", false),
        ShoppingItem("6", "шесть", false),
        ShoppingItem("7", "семь", false),
        ShoppingItem("8", "восемь", false),
        ShoppingItem("9", "девять", false),
        ShoppingItem("10", "десять", false),
        ShoppingItem("11", "одиннадцать", false),
        ShoppingItem("12", "двенадцать", false),
        ShoppingItem("13", "тринадцать", false),
        ShoppingItem("14", "четырнадцать", false),
        ShoppingItem("15", "пятнадцать", false),
        ShoppingItem("16", "шестнадцать", false),
    )

    fun getItems() = mockItems

    fun onChangeCheckedState(id: String) {
        val index = mockItems.indexOfFirst { it.id == id }
        if (index > -1) {
            val item = mockItems.get(index)
            mockItems.set(
                index = index,
                item.copy(isChecked = item.isChecked.not())
            )
        }
    }

    fun getItem(id: String): ShoppingItem? {
        return mockItems.firstOrNull { id == it.id }
    }

    fun updateItem(newItem: ShoppingItem) {
        val index = mockItems.indexOfFirst { it.id == newItem.id }
        if (index > -1) {
            mockItems.set(
                index = index,
                newItem
            )
        }
    }

    fun createNewItem(item: ShoppingItem) {
        val index = mockItems.size + 1
        mockItems.add(item.copy(id = index.toString()))
    }
}
