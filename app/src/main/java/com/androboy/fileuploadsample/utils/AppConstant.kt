package com.androboy.fileuploadsample.utils

/**
 *AppConstant : this file is to contains all constant values
 * */
class AppConstant {
    companion object {
        const val BASE_URL = "https://upload.imagekit.io/api/v1/files/"

        var IS_FROM_CAMERA = "camera"

        var CAPTURE_IMAGE = 0
        var GALLARY_IMAGE = 1

        var IMAGE_CAMERA_PROFILE = 100
        var IMAGE_GALLERY_PROFILE = 200

        var IMAGE_CAMERA_BEFORE = 300
        var IMAGE_CAMERA_AFTER = 500

        var CAMERA_PERMISSION = 2
        var WRITE_EXTERNAL_PERMISSION = 3

        var FILE_PATH_IMAGE = "filePathImage"
        var UPLOAD_MEDIA_1 = "file"
    }
}