package com.example.repositorymvvm.base

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.example.repositorymvvm.R

class ProgressDialogFragment : DialogFragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.progress_dialog_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        if (arguments?.getBoolean(ANIMATED, false) == true)
            dialog?.window?.attributes?.windowAnimations = R.style.DialogAnimation
    }


    override fun onStart() {
        super.onStart()

        dialog?.window?.run {
            setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        }
    }


    companion object {
        const val TAG = "progressDialog"
        private const val ANIMATED = "progressDialogAnimated"

        fun newInstance(animated: Boolean = false): ProgressDialogFragment {
            return ProgressDialogFragment().apply {
                arguments = Bundle().apply {
                    putBoolean(ANIMATED, animated)
                }
            }
        }
    }
}