package com.androboy.fileuploadsample.repository

import com.androboy.fileuploadsample.model.base.Errors
import com.androboy.fileuploadsample.utils.NetworkResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.ResponseBody
import retrofit2.HttpException
import retrofit2.Response
import java.io.IOException

abstract class BaseRepo {

    // we'll use this function in all
    // repos to handle api errors.
    suspend fun <T> safeApiCall(apiToBeCalled: suspend () -> Response<T>): NetworkResult<T> {

        // Returning api response
        // wrapped in Resource class
        return withContext(Dispatchers.IO) {
            try {

                // Here we are calling api lambda
                // function that will return response
                // wrapped in Retrofit's Response class
                val response: Response<T> = apiToBeCalled()

                if (response.isSuccessful) {
                    // In case of success response we
                    // are returning Resource.Success object
                    // by passing our data in it.
                    NetworkResult.Success(data = response.body()!!)
                } else {
                    // parsing api's own custom json error
                    // response in ExampleErrorResponse pojo
                    val errorResponse: Errors? = convertErrorBody(response.errorBody())
                    // Simply returning api's own failure message
                    NetworkResult.Error(
                        message = errorResponse?.message ?: "Something went wrong"
                    )
                }

            } catch (e: HttpException) {
                // Returning HttpException's message
                // wrapped in Resource.Error
                NetworkResult.Error(message = e.message ?: "Something went wrong")
            } catch (e: IOException) {
                // Returning no internet message
                // wrapped in Resource.Error
                NetworkResult.Error("Please check your network connection")
            } catch (e: Exception) {
                // Returning 'Something went wrong' in case
                // of unknown error wrapped in Resource.Error
                NetworkResult.Error(message = "Something went wrong")
            }
        }
    }

    // If you don't wanna handle api's own
    // custom error response then ignore this function
    private fun convertErrorBody(errorBody: ResponseBody?): Errors {
        val errors: Errors = Errors()
        errors.message = "Getting Error"
        return errors

    }
}