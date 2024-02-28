package com.example.myapplication.shared.main

import com.example.myapplication.shared.models.ShoppingItem

class MainInteractor {
    private val mockItems = listOf(
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

    fun getItem(id: String): ShoppingItem? {
        return mockItems.firstOrNull { id == it.id }
    }
}
