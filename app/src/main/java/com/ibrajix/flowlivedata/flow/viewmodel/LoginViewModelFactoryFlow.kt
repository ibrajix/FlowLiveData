package com.ibrajix.flowlivedata.flow.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.ibrajix.flowlivedata.flow.repo.LoginRepoFlow
import com.ibrajix.flowlivedata.retrofit.ApiHelper

class LoginViewModelFactoryFlow (private val apiHelper: ApiHelper): ViewModelProvider.Factory  {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {

        if (modelClass.isAssignableFrom(LoginViewModelFlow::class.java)) {
            return LoginViewModelFlow(LoginRepoFlow(apiHelper)) as T
        }
        throw IllegalArgumentException("Unknown class name")

    }

}