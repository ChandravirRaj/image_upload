package com.androboy.fileuploadsample.api

import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

class APIInterceptor @Inject constructor() : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()
        val builder = originalRequest.newBuilder()
        val oldReq = builder
            .addHeader("authorization", "Basic YWRtaW46YWRtaW4=")
            .build()
        return chain.proceed(oldReq)
    }
}