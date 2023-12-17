package com.androboy.fileuploadsample.utils

sealed class NetworkResult<T>(val data: T? = null, message: String? = null) {
    class Success<T>(data: T) : NetworkResult<T>(data = data)
    class Error<T>(message: String?) : NetworkResult<T>(message = message)
    class Loading<T> : NetworkResult<T>()
}
