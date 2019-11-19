package com.example.repositorymvvm.base

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.SupervisorJob
import kotlin.coroutines.CoroutineContext

interface Scope : CoroutineScope {

    class Impl(override var coroutineDispatchers: CoroutineDispatchers) : Scope {
        override lateinit var job: Job
    }


    var job: Job
    var coroutineDispatchers: CoroutineDispatchers


    override val coroutineContext: CoroutineContext
        get() =  coroutineDispatchers.uiDispatcher + job


    fun initScope() {
        job = SupervisorJob()
    }


    fun destroyScope() {
        job.cancel()
    }
}