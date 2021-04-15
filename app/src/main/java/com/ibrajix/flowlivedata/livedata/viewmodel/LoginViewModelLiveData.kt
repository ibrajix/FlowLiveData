package com.ibrajix.flowlivedata.livedata.viewmodel

import androidx.lifecycle.*
import com.ibrajix.flowlivedata.model.UserModel
import com.ibrajix.flowlivedata.livedata.repo.LoginRepoLiveData
import com.ibrajix.flowlivedata.response.LoginResponse
import com.ibrajix.flowlivedata.utils.Resource
import kotlinx.coroutines.launch


class LoginViewModelLiveData(private val loginRepoLiveData: LoginRepoLiveData) : ViewModel() {

    //cached
    private val _login = MutableLiveData<Resource<LoginResponse>>()

    //public
    val login : LiveData<Resource<LoginResponse>> get() =  _login


    //do login
    fun doLogin(userModel: UserModel) =
        viewModelScope.launch {
            try {
                _login.value = loginRepoLiveData.login(userModel)
            }
            catch (exception: Exception){

            }
        }

}