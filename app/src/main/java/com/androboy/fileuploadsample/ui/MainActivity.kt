package com.androboy.fileuploadsample.ui

import android.util.Log
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.viewbinding.ViewBinding
import com.androboy.fileuploadsample.BaseActivity
import com.androboy.fileuploadsample.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : BaseActivity() {
    override fun layoutRes(): ViewBinding {
        installSplashScreen()
        return ActivityMainBinding.inflate(layoutInflater)
    }

    override fun initView() {
        Log.d("TAG", "initView: ")
    }


}