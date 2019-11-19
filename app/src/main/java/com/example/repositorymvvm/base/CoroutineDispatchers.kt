package com.example.repositorymvvm.base

import kotlinx.coroutines.CoroutineDispatcher

class CoroutineDispatchers (
    val uiDispatcher: CoroutineDispatcher,
    val ioDispatcher: CoroutineDispatcher,
    val defaultDispatcher: CoroutineDispatcher
)