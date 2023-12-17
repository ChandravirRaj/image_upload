package com.androboy.fileuploadsample.ui

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.androboy.fileuploadsample.R
import com.androboy.fileuploadsample.SampleApp.Companion.INSTANCE
import com.androboy.fileuploadsample.callback.IDialogCallback
import com.androboy.fileuploadsample.utils.AppConstant
import com.androboy.fileuploadsample.utils.AppUtil.showToast
import com.androboy.fileuploadsample.utils.BitmapUtils
import com.androboy.fileuploadsample.utils.DialogUtils.showAlert
import com.karumi.dexter.Dexter
import com.karumi.dexter.MultiplePermissionsReport
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.DexterError
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.multi.MultiplePermissionsListener
import java.io.File

/**
 * ImageCaptureActivity : Check of gallery and camera permission with image path code
 */
class ImageCaptureActivity : AppCompatActivity() {
    private var mCapturedImageURI: Uri? = null
    private var imageFinalPath: String? = null
    private var galleryPathFile: File? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        intentData
    }

    /**
     * Check, where from is camera or gallery
     */
    private val intentData: Unit
        get() {
            if (intent != null && intent.extras != null) {
                val isCamera = intent.extras!!.getInt(AppConstant.IS_FROM_CAMERA)
                try {
                    captureMedia(isCamera)
                } catch (e: Exception) {
                    showToast(getString(R.string.text_camera_not_support))
                    finish()
                }
            }
        }

    /**
     * Check,  is camera = 0, and gallery = 1
     */
    private fun captureMedia(isCamera: Int) {
        if (isCamera == 0) {
            checkCameraPermissionForApi23()
        } else if (isCamera == 1) {
            checkGalleryPermissionForApi23()
        }
    }

    private fun checkCameraPermissionForApi23() {
        val whatPermission = ArrayList<String>()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            whatPermission.add(Manifest.permission.CAMERA)
        } else {
            whatPermission.add(Manifest.permission.CAMERA)
            whatPermission.add(Manifest.permission.WRITE_EXTERNAL_STORAGE)
        }
        Dexter.withContext(this)
            .withPermissions(whatPermission)
            .withListener(object : MultiplePermissionsListener {
                override fun onPermissionsChecked(multiplePermissionsReport: MultiplePermissionsReport) {
                    if (multiplePermissionsReport.areAllPermissionsGranted()) {
                        mCapturedImageURI = BitmapUtils.onOpenCameraImage(this@ImageCaptureActivity)
                    } else {
                        cantGetPermission()
                    }
                }

                override fun onPermissionRationaleShouldBeShown(
                    list: List<PermissionRequest>,
                    permissionToken: PermissionToken
                ) {
                    permissionToken.continuePermissionRequest()
                }
            }).check()
    }

    private fun checkGalleryPermissionForApi23() {
        val whatPermission1 = ArrayList<String>()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            whatPermission1.add(Manifest.permission.ACCESS_MEDIA_LOCATION)
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            whatPermission1.add(Manifest.permission.READ_MEDIA_IMAGES)
        } else {
            whatPermission1.add(Manifest.permission.READ_EXTERNAL_STORAGE)
            whatPermission1.add(Manifest.permission.WRITE_EXTERNAL_STORAGE)
        }
        Dexter.withContext(this)
            .withPermissions(whatPermission1)
            .withListener(object : MultiplePermissionsListener {
                override fun onPermissionsChecked(multiplePermissionsReport: MultiplePermissionsReport) {
                    if (multiplePermissionsReport.areAllPermissionsGranted()) {
                        mCapturedImageURI = BitmapUtils.onOpenGallary(this@ImageCaptureActivity)
                    } else {
                        cantGetPermission()
                    }
                }

                override fun onPermissionRationaleShouldBeShown(
                    list: List<PermissionRequest>,
                    permissionToken: PermissionToken
                ) {
                    permissionToken.continuePermissionRequest()
                }
            }).withErrorListener { dexterError: DexterError ->
                Log.d(
                    "TAG",
                    "dexterError : $dexterError"
                )
            }
            .check()
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            AppConstant.CAMERA_PERMISSION -> if (grantResults.size > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults[1] == PackageManager.PERMISSION_GRANTED) {
                mCapturedImageURI = BitmapUtils.onOpenCameraImage(this@ImageCaptureActivity)
            } else cantGetPermission()

            AppConstant.WRITE_EXTERNAL_PERMISSION -> if (grantResults.size > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) mCapturedImageURI =
                BitmapUtils.onOpenGallary(this@ImageCaptureActivity) else cantGetPermission()
        }
    }

    private fun cantGetPermission() {
        showAlert(this@ImageCaptureActivity,
            "",
            "Permission Denied! Please grant manually.",
            INSTANCE.getString(R.string.text_ok),
            INSTANCE.getString(R.string.text_button_cancel),
            object : IDialogCallback {
                override fun onClick(isOk: Boolean) {
                    if (isOk) {
                        val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
                        val uri = Uri.fromParts("package", packageName, null)
                        intent.data = uri
                        startActivity(intent)
                    }
                    finish()
                }
            })
    }

    /**
     * Check camera/gallery path using all possible devices
     *
     * @param data intent with path of image
     */
    public override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == RESULT_OK) {
            when (requestCode) {
                AppConstant.CAPTURE_IMAGE -> {
                    imageFinalPath = BitmapUtils.getPath(this, data, mCapturedImageURI)
                    if (imageFinalPath == null) {
                        if (data != null) {
                            val uriImage = data.data
                            galleryPathFile = File(uriImage!!.path)
                        }
                    } else {
                        val imgFile = File(imageFinalPath)
                        val myBitmap = BitmapFactory.decodeFile(imgFile.absolutePath)
                        galleryPathFile = if (myBitmap != null) {
                            val bitmap = BitmapUtils.imageOreintationValidator(
                                BitmapUtils.getScaledBitmap(myBitmap),
                                imageFinalPath
                            )
                            BitmapUtils.saveBitmap(bitmap)
                        } else {
                            File(imageFinalPath)
                        }
                    }
                }

                AppConstant.GALLARY_IMAGE -> {
                    imageFinalPath = BitmapUtils.getPath(this, data, mCapturedImageURI)
                    if (imageFinalPath == null) {
                        if (data != null) {
                            val uriImage = data.data
                            galleryPathFile = File(uriImage!!.path)
                        }
                    } else {
                        galleryPathFile = File(imageFinalPath)
                    }
                }

                else -> {}
            }
        }
        val intent = Intent()
        val bundle = Bundle()
        bundle.putSerializable(AppConstant.FILE_PATH_IMAGE, galleryPathFile)
        intent.putExtras(bundle)
        setResult(RESULT_OK, intent)
        finish()
    }

    /**
     * finish this screen and get callback where it called from and pass image path in actviti result method
     */
    override fun onBackPressed() {
        finish()
    }
}