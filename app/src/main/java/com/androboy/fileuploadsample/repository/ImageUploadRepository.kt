package com.androboy.fileuploadsample.repository

import com.androboy.fileuploadsample.api.ApiRequests
import com.androboy.fileuploadsample.ui.model.ImageUploadResponse
import com.androboy.fileuploadsample.utils.AppConstant
import com.androboy.fileuploadsample.utils.AppUtil
import com.androboy.fileuploadsample.utils.NetworkResult
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File
import javax.inject.Inject

class ImageUploadRepository @Inject constructor(private val apiInterface: ApiRequests) : BaseRepo() {

    suspend fun uploadImage(file: File): NetworkResult<ImageUploadResponse> {
        val requestFileSocialImage = file
            .asRequestBody(AppUtil.getMimeType(file.absolutePath)?.toMediaTypeOrNull())

        val socialImageMultipart: MultipartBody.Part = MultipartBody.Part.createFormData(
            AppConstant.UPLOAD_MEDIA_1,
            file.name,
            requestFileSocialImage
        )

        return safeApiCall { apiInterface.uploadImage(socialImageMultipart) }
    }


}