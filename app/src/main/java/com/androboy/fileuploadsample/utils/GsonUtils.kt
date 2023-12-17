package com.androboy.fileuploadsample.utils

import com.google.gson.Gson
import java.lang.reflect.Type

/**
 * Class is used to object to json string conversion and vice versa.
 */
object GsonUtils {
    @JvmStatic
    fun <T> parseJson(json: String?, type: Type?): T {
        return Gson().fromJson(json, type)
    }

    @JvmStatic
    fun getJson(profile: Any?): String {
        return Gson().toJson(profile)
    }

    fun getJsonString(model: Any?): String {
        val gson = Gson()
        return gson.toJson(model)
    }
}