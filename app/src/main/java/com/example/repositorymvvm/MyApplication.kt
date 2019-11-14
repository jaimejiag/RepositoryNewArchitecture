package com.example.repositorymvvm

import android.app.Application

class MyApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        initDI()
        AppComponent()
    }
}