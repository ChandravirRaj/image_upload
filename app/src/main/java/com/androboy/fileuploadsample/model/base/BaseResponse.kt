package com.androboy.fileuploadsample.model.base

class BaseResponse<T> {
    var statusCode = 0
    var data: T? = null
    var error: Errors? = null
}