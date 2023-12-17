package com.androboy.fileuploadsample.api

import com.androboy.fileuploadsample.ui.model.ImageUploadResponse
import okhttp3.MultipartBody
import retrofit2.Response
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

interface ApiRequests {
    @Multipart
    @POST("upload")
    suspend fun uploadImage(@Part file: MultipartBody.Part): Response<ImageUploadResponse>
}