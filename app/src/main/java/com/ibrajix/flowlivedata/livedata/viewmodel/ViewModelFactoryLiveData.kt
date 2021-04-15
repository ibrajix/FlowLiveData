package com.ibrajix.flowlivedata.livedata.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.ibrajix.flowlivedata.livedata.repo.LoginRepoLiveData
import com.ibrajix.flowlivedata.retrofit.ApiHelper

class ViewModelFactoryLiveData(private val apiHelper: ApiHelper): ViewModelProvider.Factory  {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(LoginViewModelLiveData::class.java)) {
            return LoginViewModelLiveData(LoginRepoLiveData(apiHelper)) as T
        }
        throw IllegalArgumentException("Unknown class name")
    }

}