package com.example.repositorymvvm.base

import android.os.Parcelable
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.Job

abstract class BaseViewModel<State : Parcelable, Transition> : ViewModel() {
    protected val mViewState: MutableLiveData<State> = MutableLiveData()
    protected val mViewTransition: SingleLiveEvent<Transition> = SingleLiveEvent()

    private var mServiceCall: Job? = null


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


    abstract fun hasInitValues(): Boolean
    abstract fun resetTransition()
    abstract fun init()
}