package com.ibrajix.flowlivedata.retrofit

import com.ibrajix.flowlivedata.BuildConfig
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiClient {

    private const val BASE_URL = "https://your-api-here/"

    const val LOGIN = "login-url-here"

    private fun getRetrofit(): Retrofit {

       return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(
                OkHttpClient.Builder().also { client ->
                    val logging = HttpLoggingInterceptor()
                    if (BuildConfig.DEBUG) {
                        logging.setLevel(HttpLoggingInterceptor.Level.BODY)
                    }
                    client.addInterceptor(logging)
                }.build()
            )
           .addConverterFactory(GsonConverterFactory.create())
            .build()


    }

    val apiService: ApiInterface = getRetrofit().create(ApiInterface::class.java)

}