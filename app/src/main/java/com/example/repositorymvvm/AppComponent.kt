package com.example.repositorymvvm

import com.example.repositorymvvm.base.BaseConstants
import org.koin.core.KoinComponent

class AppComponent : KoinComponent {

    init {
        getKoin().setProperty(BaseConstants.SERVER_URL, "https://api.github.com/")
    }
}