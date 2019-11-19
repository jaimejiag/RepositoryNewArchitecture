package com.example.repositorymvvm.base

import android.os.Bundle
import android.os.Parcelable
import android.os.PersistableBundle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.example.repositorymvvm.R

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


    override fun onSaveInstanceState(outState: Bundle, outPersistentState: PersistableBundle) {
        super.onSaveInstanceState(outState, outPersistentState)
        outState.putParcelable(VIEW_STATE, mViewModel.getViewState().value)
    }


    override fun onDestroy() {
        super.onDestroy()

        clearObservers()
        clearListener()
    }


    abstract fun getLayout(): Int
    abstract fun initView()
    abstract fun initListeners()
    abstract fun manageState(state: ViewState)
    abstract fun manageTransition(transition: ViewTransition)
    abstract fun clearListener()


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


    private fun clearObservers() {
        mViewModel.getViewState().removeObservers(this)
        mViewModel.getViewTransition().removeObservers(this)
    }


    protected fun showLoading() {
        if (mProgressDialog == null)
            mProgressDialog = ProgressDialogFragment.newInstance(mDialogAnimated)

        mProgressDialog?.let { pDialog ->
            if (pDialog.dialog == null || pDialog.dialog?.isShowing == false) {
                if (pDialog.isAdded)
                    pDialog.dialog?.show()
                else
                    pDialog.show(supportFragmentManager, ProgressDialogFragment.TAG)
            }
        }
    }


    protected fun hideLoading() = mProgressDialog?.dismiss()


    protected fun showCancelableAlert(title: String, descripcion: String,
                                      positiveAction: (() -> Unit)? = null,
                                      negativeAction: (() -> Unit)? = null) {
        AlertDialog.Builder(this, R.style.AlertDialogStyle)
            .setPositiveButton(getString(R.string.accept)) { _, _ -> positiveAction?.invoke() }
            .setNegativeButton(getString(R.string.cancel)) { _, _ -> negativeAction?.invoke() }
            .setOnCancelListener { negativeAction?.invoke() }
            .setTitle(title)
            .setMessage(descripcion)
            .create()
            .show()
    }


    protected fun showNeutralAlert(title: String, description: String, action: (() -> Unit)? = null) {
        AlertDialog.Builder(this, R.style.AlertDialogStyle)
            .setNeutralButton(getString(R.string.accept)) { _, _ -> action?.invoke() }
            .setTitle(title)
            .setMessage(description)
            .create()
            .show()
    }


    protected fun showError(errorMessage: String?, title: String) {
        AlertDialog.Builder(this, R.style.AlertDialogStyle)
            .setNeutralButton(getString(R.string.accept)) { dialog, _ -> dialog.dismiss() }
            .setTitle(title)
            .setMessage(errorMessage)
            .create()
            .show()
    }


    companion object {
        private const val VIEW_STATE = "VIEW_STATE"
    }
}