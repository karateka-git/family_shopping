package com.example.myapplication.shared.main

import com.example.myapplication.shared.models.ShoppingItem

class MainInteractor {
    private var mockItems = listOf(
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
        val newItems = mutableListOf<ShoppingItem>().apply {
            addAll(mockItems)
        }
        val index = newItems.indexOfFirst { it.id == id }
        if (index > -1) {
            val item = newItems.get(index)
            newItems.set(
                index = index,
                item.copy(isChecked = item.isChecked.not())
            )
            mockItems = newItems
        }
    }

    fun getItem(id: String): ShoppingItem? {
        return mockItems.firstOrNull { id == it.id }
    }

    fun updateItem(newItem: ShoppingItem) {
        val newItems = mockItems as MutableList
        val index = newItems.indexOfFirst { it.id == newItem.id }
        if (index > -1) {
            newItems.set(
                index = index,
                newItem
            )
        }
        mockItems = newItems
    }

    fun createNewItem(item: ShoppingItem): ShoppingItem {
        val newItems = mutableListOf<ShoppingItem>().apply {
            addAll(mockItems)
        }
        val newItem = item.copy(id = (newItems.size + 1).toString())
        newItems.add(newItem)
        mockItems = newItems
        return newItem
    }
}
