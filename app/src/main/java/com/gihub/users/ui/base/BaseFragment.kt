package com.gihub.users.ui.base

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import dagger.android.support.AndroidSupportInjection

abstract class BaseFragment <out V: ViewModel>: Fragment() {

    private var mActivity: BaseActivity<*>? = null
    private var mViewModel:V? = null
    private lateinit var mActivityContext: AppCompatActivity

    private lateinit var mProgressDialog: Dialog

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        mViewModel = getViewModel()
        setHasOptionsMenu(false)
    }

    override fun onAttach(context: Context) {
        performDependencyInjection()
        super.onAttach(context)

        if (context is BaseActivity<*>) {
            val activity = context as BaseActivity<*>?
            this.mActivity = activity
            activity!!.onFragmentAttached()
        }
        if (context is AppCompatActivity) {
            mActivityContext = context
        }
    }

    override fun onDetach() {
        mActivity = null
        super.onDetach()
    }

    private fun performDependencyInjection() {
        AndroidSupportInjection.inject(this)
    }

    interface Callback {

        fun onFragmentAttached()

        fun onFragmentDetached(tag: String)
    }

    /**
     * Override for set view model
     *
     * @return view model instance
     */

    abstract fun getViewModel(): V
}