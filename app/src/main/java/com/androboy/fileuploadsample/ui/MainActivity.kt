package com.androboy.fileuploadsample.ui

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.viewbinding.ViewBinding
import com.androboy.fileuploadsample.BaseActivity
import com.androboy.fileuploadsample.R
import com.androboy.fileuploadsample.databinding.ActivityMainBinding
import com.androboy.fileuploadsample.databinding.BottomSheetCaptureBinding
import com.androboy.fileuploadsample.ui.viewmodel.MainViewModel
import com.androboy.fileuploadsample.utils.AppConstant
import com.androboy.fileuploadsample.utils.AppUtil
import com.androboy.fileuploadsample.utils.NetworkResult
import com.google.android.material.bottomsheet.BottomSheetDialog
import dagger.hilt.android.AndroidEntryPoint
import java.io.File

@AndroidEntryPoint
class MainActivity : BaseActivity() {
    lateinit var ui: ActivityMainBinding
    private lateinit var uiBottom: BottomSheetCaptureBinding
    private lateinit var viewModel: MainViewModel
    private var mCameraSheet: BottomSheetDialog? = null
    private var selectionCamera = 0
    private var pickedImageFile: File? = null
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
        viewModel.uploadImageLiveData.observe(this, Observer {
            when (it) {
                is NetworkResult.Success -> {
                    hideProgressBar()

                }

                is NetworkResult.Error -> {
                    hideProgressBar()
                    showSnackBar("Your account cannot be authenticated. For support kindly contact us at support@imagekit.io .")
                }

                is NetworkResult.Loading -> {
                    Log.d("MMMMMM", "setObserver Loading: ")
                }
            }
        })

    }

    private fun setListeners() {
        ui.btnPickImage.setOnClickListener {
            AppUtil.preventTwoClick(it)
            selectionCamera = 0
            mCameraSheet?.show()
        }

        ui.btnUploadImage.setOnClickListener {
            AppUtil.preventTwoClick(it)
            if (AppUtil.isConnection()) {
                if (pickedImageFile != null) {
                    showProgressBar()
                    viewModel.uploadImage(pickedImageFile!!)
                } else {
                    showSnackBar("Please pick image first")
                }
            } else {
                showSnackBar("Please check your network")
            }
        }

        uiBottom.linearFromCamera.setOnClickListener {
            AppUtil.preventTwoClick(it)
            mCameraSheet?.dismiss()
            if (AppUtil.isConnection()) {
                val bundle = Bundle()
                val intent = Intent(this, ImageCaptureActivity::class.java)
                bundle.putInt(AppConstant.IS_FROM_CAMERA, 0)
                intent.putExtras(bundle)
                when (selectionCamera) {
                    0 -> startActivityForResult(intent, AppConstant.IMAGE_CAMERA_PROFILE)
                    1 -> startActivityForResult(intent, AppConstant.IMAGE_CAMERA_BEFORE)
                    2 -> startActivityForResult(intent, AppConstant.IMAGE_CAMERA_AFTER)
                }
            } else {
                showSnackBar(getString(R.string.text_msg_network_connection))
            }
        }

        uiBottom.linearFromGallery.setOnClickListener {
            AppUtil.preventTwoClick(it)
            mCameraSheet?.dismiss()

            if (AppUtil.isConnection()) {
                val bundle = Bundle()
                val intent = Intent(this, ImageCaptureActivity::class.java)
                bundle.putInt(AppConstant.IS_FROM_CAMERA, 1)
                intent.putExtras(bundle)
                when (selectionCamera) {
                    0 -> startActivityForResult(intent, AppConstant.IMAGE_CAMERA_PROFILE)
                    1 -> startActivityForResult(intent, AppConstant.IMAGE_CAMERA_BEFORE)
                    2 -> startActivityForResult(intent, AppConstant.IMAGE_CAMERA_AFTER)
                }
            } else {
                showSnackBar(getString(R.string.text_msg_network_connection))
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (AppUtil.isConnection()) {
            if (RESULT_OK == resultCode) {
                if (data != null) {
                    val bundle = data.extras
                    if (bundle != null) {
                        when (requestCode) {
                            AppConstant.IMAGE_CAMERA_PROFILE, AppConstant.IMAGE_GALLERY_PROFILE -> {
                                val finalFile =
                                    data.extras?.getSerializable(AppConstant.FILE_PATH_IMAGE) as File?

                                val finalFilePath =
                                    data.extras?.getSerializable(AppConstant.FILE_PATH_IMAGE)
                                if (finalFile != null) {
                                    val sizeMb = finalFile.length().toInt() * Math.pow(2.0, -20.0)
                                        .toInt()
                                    if (sizeMb >= 10) {
                                        showSnackBar("Image size is more than 10 mb")
                                    } else {
                                        pickedImageFile = finalFile

                                        ui.tvFilePath.text = finalFilePath.toString()
                                        ui.tvFileName.text = finalFile.name
                                        ui.IvImagePreview.post {
                                            AppUtil.loadImage(
                                                ui.IvImagePreview,
                                                finalFile
                                            )
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        } else {
            showSnackBar(getString(R.string.text_msg_network_connection))
        }
    }


}