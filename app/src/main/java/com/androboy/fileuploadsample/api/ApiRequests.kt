package com.androboy.fileuploadsample.api

import com.androboy.fileuploadsample.ui.model.ImageUploadResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiRequests {
    @GET("/imageUpload")
    suspend fun uploadImage(@Query("page") page: Int): Response<ImageUploadResponse>
}