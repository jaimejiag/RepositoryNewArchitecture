package com.example.repositorymvvm

import android.app.Application
import com.example.repositorymvvm.base.baseModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.dsl.module

fun Application.initDI() {
    startKoin {
        androidLogger()
        androidContext(this@initDI)
        modules(listOf(myModule, baseModule))
    }
}


val myModule = module {

}