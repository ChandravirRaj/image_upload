package com.androboy.fileuploadsample.utils

class AppConstant {
    companion object{
        const val BASE_URL = "https://api.quotable.io/"

        var IS_FROM_CAMERA = "camera"

        var CAPTURE_IMAGE = 0
        var GALLARY_IMAGE = 1

        var IMAGE_CAMERA_PROFILE = 100
        var IMAGE_GALLERY_PROFILE = 200

        var IMAGE_CAMERA_BEFORE = 300
        var IMAGE_GALLERY_BEFORE = 400
        var IMAGE_CAMERA_AFTER = 500

        var CAMERA_PERMISSION = 2
        var WRITE_EXTERNAL_PERMISSION = 3

        var FILE_PATH_IMAGE = "filePathImage"
    }
}