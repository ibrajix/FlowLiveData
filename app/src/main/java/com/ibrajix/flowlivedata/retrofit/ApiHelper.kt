package com.ibrajix.flowlivedata.retrofit

import com.ibrajix.flowlivedata.model.UserModel

class ApiHelper(private val apiInterface: ApiInterface) {

    suspend fun login(userModel: UserModel) = apiInterface.login(userModel)

}