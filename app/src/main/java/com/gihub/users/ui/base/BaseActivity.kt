package com.gihub.users.ui.base

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.View
import android.view.Window
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModel
import com.gihub.users.R
import com.gihub.users.widgets.NetworkUtils
import dagger.android.AndroidInjection
abstract class BaseActivity <out V: ViewModel>: AppCompatActivity(), BaseFragment.Callback {

    private lateinit var mViewModel: V
    private lateinit var progressDialog: Dialog

    override fun onCreate(savedInstanceState: Bundle?) {
        performDependencyInjection()
        super.onCreate(savedInstanceState)
        initializeProgressLoader()
    }

    private fun initializeProgressLoader() {
        progressDialog = Dialog(this)
        progressDialog.setCancelable(false)
        progressDialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        progressDialog.window.setBackgroundDrawableResource(android.R.color.transparent)
        progressDialog.setContentView(R.layout.dialog_progress)
    }

    fun setProgressVisibility(visible: Boolean) {
        if (visible) {
            progressDialog.show()
        } else {
            progressDialog.dismiss()
        }
    }

    fun showInfoToast(msg: String){
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
    }

    private fun performDependencyInjection() {
        AndroidInjection.inject(this)
        mViewModel = getViewModel()
    }

    override fun onFragmentAttached() {

    }

    override fun onFragmentDetached(tag: String) {

    }


    fun isNetworkConnected(): Boolean {
        val flag  = NetworkUtils.isNetworkConnected(applicationContext)
        if(!flag) {
            showInfoToast("Internet not connected!")
        }
        return true
    }

    fun hideKeyboard() {
        val view: View? = this.currentFocus
        val inputMethodManager: InputMethodManager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(view?.windowToken, 0)
    }

    /**
     * Override for set view model
     *
     * @return ViewModel instance
     * */
    abstract fun getViewModel(): V
}