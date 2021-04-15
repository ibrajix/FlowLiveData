package com.ibrajix.flowlivedata.livedata.repo

import com.ibrajix.flowlivedata.model.UserModel
import com.ibrajix.flowlivedata.retrofit.ApiHelper
import com.ibrajix.flowlivedata.utils.BaseDataSource

class LoginRepoLiveData(private val apiHelper: ApiHelper) : BaseDataSource() {

    suspend fun login(userModel: UserModel) =  safeApiCall { apiHelper.login(userModel) }

}