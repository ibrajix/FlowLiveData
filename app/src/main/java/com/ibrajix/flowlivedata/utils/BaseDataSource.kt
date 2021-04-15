package com.ibrajix.flowlivedata.utils

import com.google.gson.Gson
import com.ibrajix.flowlivedata.response.LoginResponse
import retrofit2.Response

abstract class BaseDataSource {

    suspend fun <T> safeApiCall(apiCall: suspend () -> Response<T>): Resource<T> {

        try {
            val response = apiCall()

            if (response.isSuccessful) {
                val body = response.body()
                if (body != null) {
                    return Resource.success(body)
                }
            }

            else{
                val message: LoginResponse = Gson().fromJson(response.errorBody()!!.charStream(), LoginResponse::class.java)
                return Resource.error(message.message)
            }

            return Resource.failed("Something went wrong, try again jaa")

        } catch (e: Exception) {
            return Resource.failed("Something went wrong, $e")
        }
    }


}