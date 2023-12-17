package com.androboy.fileuploadsample.ui

import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.ViewModelProvider
import androidx.viewbinding.ViewBinding
import com.androboy.fileuploadsample.BaseActivity
import com.androboy.fileuploadsample.databinding.ActivityMainBinding
import com.androboy.fileuploadsample.databinding.BottomSheetCaptureBinding
import com.androboy.fileuploadsample.ui.viewmodel.MainViewModel
import com.google.android.material.bottomsheet.BottomSheetDialog
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : BaseActivity() {
    lateinit var ui: ActivityMainBinding
    private lateinit var uiBottom: BottomSheetCaptureBinding
    private lateinit var viewModel: MainViewModel
    private var mCameraSheet: BottomSheetDialog? = null
    private var selectionCamera = 0
    override fun layoutRes(): ViewBinding {
        installSplashScreen()
        return ActivityMainBinding.inflate(layoutInflater)
    }

    override fun initView() {
        ui = binding as ActivityMainBinding
        viewModel = ViewModelProvider(this)[MainViewModel::class.java]

        mCameraSheet = BottomSheetDialog(this)
        uiBottom = BottomSheetCaptureBinding.inflate(layoutInflater)
        mCameraSheet?.setContentView(uiBottom.root)

        setObservers()
        setListeners()
    }

    private fun setObservers() {


    }

    private fun setListeners() {
        ui.btnPickImage.setOnClickListener {
            selectionCamera = 0
            mCameraSheet?.show()
        }
    }


}