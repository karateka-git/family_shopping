package com.example.myapplication.shared.di

import com.example.myapplication.shared.main.MainInteractor
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val interactorModules = module {
    singleOf(::MainInteractor)
}
