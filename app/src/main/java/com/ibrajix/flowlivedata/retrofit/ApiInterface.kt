package com.ibrajix.flowlivedata.retrofit

import com.ibrajix.flowlivedata.model.UserModel
import com.ibrajix.flowlivedata.response.LoginResponse
import com.ibrajix.flowlivedata.retrofit.ApiClient.LOGIN
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface ApiInterface {

    //user login
    @POST(LOGIN)
    suspend fun login(
        @Body userModel: UserModel
    )     : Response<LoginResponse>

}