package com.ibrajix.flowlivedata.flow.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ibrajix.flowlivedata.model.UserModel
import com.ibrajix.flowlivedata.flow.repo.LoginRepoFlow
import com.ibrajix.flowlivedata.response.LoginResponse
import com.ibrajix.flowlivedata.utils.Resource
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class LoginViewModelFlow(private val loginRepoFlow: LoginRepoFlow) : ViewModel() {

    private val _loginUserFlow = Channel<Resource<LoginResponse>>(Channel.BUFFERED)
    val loginUserFlow = _loginUserFlow.receiveAsFlow()

    fun doLoginUserFlow(userModel: UserModel){

        viewModelScope.launch {
            loginRepoFlow.loginUserFlow(userModel)
                .catch { e ->
                    _loginUserFlow.send(Resource.error(e.toString()))
                }
                .collect {
                    _loginUserFlow.send(it)
                }
        }

    }
}