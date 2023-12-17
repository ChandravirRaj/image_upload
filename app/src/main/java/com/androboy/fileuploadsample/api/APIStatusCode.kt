package com.androboy.fileuploadsample.api

/**
 * APIStatusCode this class contains all status those are comes i api response.
 * These are helpful for decision making
 * */
class APIStatusCode {

    companion object {
        var OK = 200
        var CREATED = 201
        var NO_CONTENT = 204
        var BAD_REQUEST = 400
        var AUTHENTICATION_FAILED = 401
        var RECORD_NOT_FOUND = 404
        var CONFLICT = 409
        var SERVER_ERROR = 500
        var SLOW_NETWORK_ERROR = 600
        var DELETED = 410
    }
}