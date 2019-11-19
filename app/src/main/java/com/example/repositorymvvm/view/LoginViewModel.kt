package com.example.repositorymvvm.view

import com.example.repositorymvvm.base.BaseViewModel
import com.example.repositorymvvm.base.CoroutineDispatchers

class LoginViewModel (
    mDispatchers: CoroutineDispatchers
) : BaseViewModel<LoginViewState, LoginViewTransition>(
    mDispatchers = mDispatchers
) {

    override fun hasInitValues(): Boolean = true


    override fun resetTransition() {
        //Nothing to implement
    }


    override fun init() {
        //Nothing to implement
    }
}