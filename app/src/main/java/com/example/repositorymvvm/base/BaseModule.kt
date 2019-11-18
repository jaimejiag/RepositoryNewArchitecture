package com.example.repositorymvvm.base

import com.example.repositorymvvm.BuildConfig
import com.example.repositorymvvm.base.helper.NetworkHelper
import com.google.gson.Gson
import com.itkacher.okhttpprofiler.OkHttpProfilerInterceptor
import okhttp3.OkHttpClient
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val baseModule = module {

    factory { NetworkHelper(androidContext()) }

    single { Gson() }

    single {
        val url = getProperty<String>(BaseConstants.SERVER_URL)

        val okHttpClient = run {
            val okHttpClient = OkHttpClient.Builder()

            if (BuildConfig.DEBUG)
                okHttpClient.addInterceptor(OkHttpProfilerInterceptor())

            okHttpClient
        }

        val retrofitBuilder = Retrofit.Builder()
            .baseUrl(url)
            .addConverterFactory(GsonConverterFactory.create())

        val retrofit = retrofitBuilder.build()

        RetrofitNetwork(
            mHttpClient = okHttpClient,
            mRetrofit = retrofit
        )
    }
}