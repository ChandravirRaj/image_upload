package com.androboy.fileuploadsample.ui

import android.util.Log
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.ViewModelProvider
import androidx.viewbinding.ViewBinding
import com.androboy.fileuploadsample.BaseActivity
import com.androboy.fileuploadsample.databinding.ActivityMainBinding
import com.androboy.fileuploadsample.ui.viewmodel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : BaseActivity() {
    lateinit var ui: ActivityMainBinding
    private lateinit var viewModel: MainViewModel
    override fun layoutRes(): ViewBinding {
        installSplashScreen()
        return ActivityMainBinding.inflate(layoutInflater)
    }

    override fun initView() {
        Log.d("TAG", "initView: ")
        ui = binding as ActivityMainBinding
        viewModel = ViewModelProvider(this)[MainViewModel::class.java]
        setObservers()

        setListeners()
    }

    private fun setObservers() {


    }

    private fun setListeners() {
        ui.btnPickImage.setOnClickListener {

        }
    }


}