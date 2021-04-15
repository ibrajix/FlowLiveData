package com.ibrajix.flowlivedata.livedata.view

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.ibrajix.flowlivedata.model.UserModel
import com.ibrajix.flowlivedata.retrofit.ApiClient
import com.ibrajix.flowlivedata.retrofit.ApiHelper
import com.ibrajix.flowlivedata.utils.Resource
import com.ibrajix.flowlivedata.utils.Utility
import com.ibrajix.flowlivedata.flow.view.FlowActivity
import com.ibrajix.flowlivedata.livedata.viewmodel.LoginViewModelLiveData
import com.ibrajix.flowlivedata.livedata.viewmodel.ViewModelFactoryLiveData
import com.ibrajix.flowlivedata.databinding.ActivityLiveDataBinding

class LiveDataActivity : AppCompatActivity() {

    private lateinit var viewModelLiveData: LoginViewModelLiveData

    private lateinit var binding: ActivityLiveDataBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLiveDataBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        initView()

    }

    private fun initView(){

        setupViewModel()
        handleClick()

    }

    private fun handleClick(){

        //on click login button
        binding.btnLogin.setOnClickListener {

            binding.btnLogin.visibility = View.GONE
            binding.loading.visibility = View.VISIBLE

            setupObservers()

        }

        //on click use flow text
        binding.txtUseFlow.setOnClickListener {

            val intent = Intent(this, FlowActivity::class.java)
            startActivity(intent)

        }

    }

    private fun setupViewModel(){

        viewModelLiveData = ViewModelProviders.of(
            this,
            ViewModelFactoryLiveData(ApiHelper(ApiClient.apiService))
        ).get(LoginViewModelLiveData::class.java)

    }

    private fun setupObservers(){

        //get email and password
        val email = binding.editText.text.toString()
        val password = binding.editText2.text.toString()

        val userModel = UserModel(email = email, password = password)

        viewModelLiveData.doLogin(userModel)
        viewModelLiveData.login.observe(this, Observer {

            when(it.status){

                Resource.Status.SUCCESS -> {

                    if(it.data?.status == "fail"){

                        binding.loading.visibility = View.GONE
                        binding.btnLogin.visibility = View.VISIBLE
                        Utility.displaySnackBar(binding.root, it.data.message, this)

                    }

                    else if (it.data?.status == "success"){

                        Toast.makeText(this, "Login was successful", Toast.LENGTH_LONG).show()

                        binding.loading.visibility = View.GONE
                        binding.btnLogin.visibility = View.VISIBLE


                    }
                }

                Resource.Status.ERROR -> {

                    binding.loading.visibility = View.GONE
                    binding.btnLogin.visibility = View.VISIBLE

                    Utility.displaySnackBar(
                        binding.root,
                        it.message?:"",
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
                        it.message?:"",
                        applicationContext
                    )

                }
            }


        })

    }



}