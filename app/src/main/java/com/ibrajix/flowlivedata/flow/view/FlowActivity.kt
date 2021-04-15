package com.ibrajix.flowlivedata.flow.view

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.*
import com.ibrajix.flowlivedata.model.UserModel
import com.ibrajix.flowlivedata.retrofit.ApiClient
import com.ibrajix.flowlivedata.retrofit.ApiHelper
import com.ibrajix.flowlivedata.utils.Resource
import com.ibrajix.flowlivedata.utils.Utility
import com.ibrajix.flowlivedata.livedata.view.LiveDataActivity
import com.ibrajix.flowlivedata.flow.viewmodel.LoginViewModelFactoryFlow
import com.ibrajix.flowlivedata.flow.viewmodel.LoginViewModelFlow
import com.ibrajix.flowlivedata.R
import com.ibrajix.flowlivedata.databinding.ActivityFlowBinding
import kotlinx.coroutines.flow.collect

class FlowActivity : AppCompatActivity() {

    private lateinit var viewModel: LoginViewModelFlow

    private lateinit var binding: ActivityFlowBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_flow)

        binding = ActivityFlowBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        initView()
    }

    private fun initView(){

        setupViewModel()
        handleClick()

    }

    private fun setupViewModel(){

        viewModel = ViewModelProviders.of(
                this,
                LoginViewModelFactoryFlow(ApiHelper(ApiClient.apiService))
        ).get(LoginViewModelFlow::class.java)

    }

    private fun handleClick(){

        //on click login button
        binding.btnLogin.setOnClickListener {

            binding.btnLogin.visibility = View.GONE
            binding.loading.visibility = View.VISIBLE

            setupObservers()

        }

        //on click use flow text
        binding.txtUseLiveDate.setOnClickListener {

            val intent = Intent(this, LiveDataActivity::class.java)
            startActivity(intent)

        }

    }

    private fun setupObservers(){

        //get email and password
        val email = binding.editText.text.toString()
        val password = binding.editText2.text.toString()

        val userModel = UserModel(email = email, password = password)

        viewModel.doLoginUserFlow(userModel)

        this.addRepeatingJob(Lifecycle.State.STARTED){

            viewModel.loginUserFlow.collect {

                when (it.status) {

                    Resource.Status.SUCCESS -> {

                        if (it.data?.status == "fail") {

                            binding.loading.visibility = View.GONE
                            binding.btnLogin.visibility = View.VISIBLE
                            Utility.displaySnackBar(binding.root, it.data.message, this@FlowActivity)

                        } else if (it.data?.status == "success") {

                            Toast.makeText(this@FlowActivity, "Login was successful", Toast.LENGTH_LONG).show()

                            binding.loading.visibility = View.GONE
                            binding.btnLogin.visibility = View.VISIBLE


                        }
                    }

                    Resource.Status.ERROR -> {

                        binding.loading.visibility = View.GONE
                        binding.btnLogin.visibility = View.VISIBLE

                        Utility.displaySnackBar(
                            binding.root,
                            it.message ?: "",
                            applicationContext
                        )

                    }

                    Resource.Status.LOADING -> {

                        binding.loading.visibility = View.VISIBLE
                        binding.btnLogin.visibility = View.GONE

                    }

                    Resource.Status.FAILURE -> {

                        binding.loading.visibility = View.GONE
                        binding.btnLogin.visibility = View.VISIBLE

                        Utility.displaySnackBar(
                            binding.root,
                            it.message ?: "",
                            applicationContext
                        )

                    }
                }

            }

        }

    }
}