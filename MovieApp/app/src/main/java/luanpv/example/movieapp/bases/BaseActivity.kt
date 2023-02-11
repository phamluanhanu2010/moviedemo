package com.pgbank.personal.bases

import android.os.Bundle
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.google.gson.Gson
import com.pgbank.personal.constants.Common
import luanpv.example.movieapp.dialogs.LoadingDialog
import org.koin.android.ext.android.inject


abstract class BaseActivity : AppCompatActivity() {
    val loading by lazy {
        LoadingDialog(this)
    }
    val gson by inject<Gson>()

    abstract val viewModel: BaseViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Common.currentActivity = (this)
        hideLoading()

        viewModel?.apply {
            isLoading.observe(this@BaseActivity, Observer {
                handleShowLoading(it ?: false)
            })
        }

    }

    override fun setContentView(layoutResID: Int) {
        super.setContentView(layoutResID)
    }

    fun hideLoading() {
        loading.dismiss()
    }

    fun showLoading() {
        if (!isFinishing)
            loading.show()
    }

    open fun handleShowLoading(isLoading: Boolean) {
        runOnUiThread {
            if (isLoading) showLoading() else hideLoading()
        }
    }
}
