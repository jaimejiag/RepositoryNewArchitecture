package com.example.repositorymvvm.base

import android.os.Parcelable
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

abstract class BaseViewModel<State : Parcelable, Transition>(
    private val mDispatchers: CoroutineDispatchers
) : ViewModel(), Scope by Scope.Impl(coroutineDispatchers = mDispatchers) {

    protected val mViewState: MutableLiveData<State> = MutableLiveData()
    protected val mViewTransition: SingleLiveEvent<Transition> = SingleLiveEvent()

    private var mServiceCall: Job? = null


    init {
        this.initScope()
    }


    override fun onCleared() {
        destroyScope()
        super.onCleared()
    }


    abstract fun hasInitValues(): Boolean
    abstract fun resetTransition()
    abstract fun init()


    fun getViewState(): LiveData<State> = mViewState

    fun getViewTransition(): LiveData<Transition> = mViewTransition


    fun setViewTransition(transition: Transition) {
        mViewTransition.value = transition
    }


    fun setViewState(state: State) {
        mViewState.value = state
    }


    fun config() {
        if (mServiceCall == null || mServiceCall?.isCompleted == true || mServiceCall?.isCancelled == true) {
            if (hasInitValues())
                resetTransition()
            else
                init()
        }
    }


    fun executeBackground(
        delay: Long = 0,
        dispatcher: CoroutineDispatcher = mDispatchers.ioDispatcher,
        param: suspend (coroutineContext: CoroutineContext) -> Unit
        ): Job {
        mServiceCall?.cancel()

        return launch(dispatcher) {
            if (delay > 0) delay(delay)
            param(this.coroutineContext)
        }.also {
            mServiceCall = it
        }
    }


    fun <T> executeBackgroundAsync(
        delay: Long = 0,
        dispatcher: CoroutineDispatcher = mDispatchers.ioDispatcher,
        param: suspend () -> T
    ): Deferred<T> {
        return async(dispatcher) {
            if (delay > 0) delay(delay)
            param()
        }
    }


    fun <T> executeBackgroundJob(
        delay: Long = 0,
        dispatcher: CoroutineDispatcher = mDispatchers.ioDispatcher,
        param: suspend () -> T
    ): Job {
        return launch(dispatcher) {
            if (delay > 0) delay(delay)
            param()
        }
    }


    fun executeUI(delay: Long = 0, param: () -> Unit) {
        launch(mDispatchers.uiDispatcher) {
            if (delay > 0) delay(delay)
            param()
        }
    }
}