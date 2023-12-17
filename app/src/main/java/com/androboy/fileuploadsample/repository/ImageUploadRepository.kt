package com.androboy.fileuploadsample.repository

import com.androboy.fileuploadsample.api.ApiRequests
import com.androboy.fileuploadsample.ui.model.ImageUploadResponse
import com.androboy.fileuploadsample.utils.NetworkResult
import javax.inject.Inject

class ImageUploadRepository @Inject constructor(private val apiInterface: ApiRequests) : BaseRepo() {

    suspend fun uploadImage(page: Int): NetworkResult<ImageUploadResponse> {
        return safeApiCall { apiInterface.uploadImage(page) }
    }


}