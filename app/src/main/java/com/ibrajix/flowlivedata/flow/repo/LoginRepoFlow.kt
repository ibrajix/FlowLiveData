package com.ibrajix.flowlivedata.flow.repo

import com.ibrajix.flowlivedata.model.UserModel
import com.ibrajix.flowlivedata.response.LoginResponse
import com.ibrajix.flowlivedata.retrofit.ApiHelper
import com.ibrajix.flowlivedata.utils.BaseDataSource
import com.ibrajix.flowlivedata.utils.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class LoginRepoFlow(private val apiHelper: ApiHelper) : BaseDataSource() {

    //login user with flow
    suspend fun loginUserFlow(userModel: UserModel) : Flow<Resource<LoginResponse>> {

        return flow {
            val result = safeApiCall { apiHelper.login(userModel) }
            emit(result)

        }.flowOn(Dispatchers.IO)

    }

}