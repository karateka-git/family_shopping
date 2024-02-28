package com.example.myapplication.shared.di

import org.koin.core.context.startKoin
import org.koin.dsl.KoinAppDeclaration

fun appModules() = listOf(interactorModules)

fun initKoin(appDeclaration: KoinAppDeclaration = {}) =
    startKoin {
        appDeclaration()
        modules(appModules())
    }
