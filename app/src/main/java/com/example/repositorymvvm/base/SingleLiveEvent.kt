package com.example.repositorymvvm.base

import androidx.annotation.MainThread
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import java.util.concurrent.atomic.AtomicBoolean

class SingleLiveEvent<T> : MutableLiveData<T>() {
    private val mPending = AtomicBoolean(false)


    @MainThread
    override fun observe(owner: LifecycleOwner, observer: Observer<in T>) {
        //Observe the internal MutableLiveData
        super.observe(owner, Observer { t ->
            if (mPending.compareAndSet(true, false)) {
                observer.onChanged(t)
            }
        })
    }


    @MainThread
    override fun setValue(value: T?) {
        mPending.set(true)
        super.setValue(value)
    }


    @MainThread
    fun call() {
        value = null
    }


    companion object {
        private const val TAG = "singleLiveEvent"
    }
}