package com.example.repositorymvvm.base

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import java.util.concurrent.TimeUnit

class RetrofitNetwork(
    private val mHttpClient: OkHttpClient.Builder,
    private val mRetrofit: Retrofit
) {

    fun <S> createService(serviceClass: Class<S>): S {
        mHttpClient.connectTimeout(10, TimeUnit.SECONDS)
        mHttpClient.readTimeout(40, TimeUnit.SECONDS)
        mHttpClient.writeTimeout(40, TimeUnit.SECONDS)

        return mRetrofit.create(serviceClass)
    }
}