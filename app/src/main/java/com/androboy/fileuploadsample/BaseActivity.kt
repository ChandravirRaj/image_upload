package com.androboy.fileuploadsample

import android.app.ProgressDialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.Gravity
import android.view.WindowManager
import android.widget.FrameLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.viewbinding.ViewBinding
import com.androboy.fileuploadsample.utils.AppUtil
import com.google.android.material.snackbar.Snackbar
import java.util.*


/**
 * BaseActivity : this one is base activity for all sub activities which are using in this project
 * it extends AppCompatActivity()
 * it has all commonly used methods and variables
 *
 * */
abstract class BaseActivity : AppCompatActivity(){

    // Root Progress bar
    private var progressDialog: ProgressDialog? = null


    // View Binding for root UI
    lateinit var binding: ViewBinding

    /**
     * Layout resource for View Binding for Activity
     */
    abstract fun layoutRes(): ViewBinding

    abstract fun initView()

    /**
     * Set All UI resource in Window
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = layoutRes()
        setContentView(binding.root)
        initView()
    }

    /**
     * Set status bar color
     */
    open fun setStatusBarColor(color: Int) {
        val window = window
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        window.statusBarColor = color
    }



    /**
     * Show progress bar
     */
    open fun showProgressBar() {
        try {
            hideProgressBar()
            if (!this.isDestroyed && !isFinishing) {
                progressDialog = ProgressDialog.show(this, "", "", true)
                if (progressDialog != null) {
                    progressDialog!!.setCanceledOnTouchOutside(false)
                    progressDialog!!.setContentView(R.layout.progress_layout)
                    Objects.requireNonNull(progressDialog!!.window)
                        ?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
                }
            }

        } catch (e: Exception) {

        }

    }

    /**
     * Hide progress bar
     */
    open fun hideProgressBar() {
        if (!this@BaseActivity.isFinishing) {
            if (progressDialog != null) {
                progressDialog?.dismiss()
                progressDialog = null
            }
        }
    }



    /**
     * show Snack bar with msg
     */
    var snack: Snackbar? = null


    fun showSnackBar(str: String) {
        if (snack != null) {
            snack?.dismiss()
        }
        try {
            snack = Snackbar.make(findViewById(android.R.id.content), str, Snackbar.LENGTH_LONG)
            val view = snack?.view
            val params = view?.layoutParams as FrameLayout.LayoutParams
            params.gravity = Gravity.TOP
            view.layoutParams = params
            view.setBackgroundColor(AppUtil.getColor(R.color.black))
            snack?.show()
        } catch (e: Exception) {

        }
    }

}