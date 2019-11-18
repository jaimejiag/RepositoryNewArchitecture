package com.example.repositorymvvm.base

import android.os.Bundle
import android.os.Parcelable
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer

abstract class BaseActivity<ViewState : Parcelable, ViewTransition, ViewModel : BaseViewModel<ViewState, ViewTransition>> (
    private val mDialogAnimated: Boolean
) : AppCompatActivity() {
    abstract val mViewModel: ViewModel
    private var mProgressDialog: ProgressDialogFragment? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(getLayout())

        if (savedInstanceState != null) {
            savedInstanceState.getParcelable<ViewState>(VIEW_STATE)?.let { viewState ->
                mViewModel.setViewState(viewState)
            }

            mProgressDialog = supportFragmentManager.findFragmentByTag(ProgressDialogFragment.TAG) as? ProgressDialogFragment?
        }

        mViewModel.config()
        initView()
        initListeners()
        initObservers()
    }


    abstract fun getLayout(): Int
    abstract fun initView()
    abstract fun initListeners()
    abstract fun manageState(state: ViewState)
    abstract fun manageTransition(transition: ViewTransition)


    private fun initObservers() {
        mViewModel.getViewState().observe(this, Observer {
            if (it != null)
                manageState(state = it)
        })

        mViewModel.getViewTransition().observe(this, Observer {
            if (it != null)
                manageTransition(transition = it)
        })
    }


    companion object {
        private const val VIEW_STATE = "VIEW_STATE"
    }
}