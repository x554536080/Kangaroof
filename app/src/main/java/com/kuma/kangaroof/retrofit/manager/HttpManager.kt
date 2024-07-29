package com.kuma.kangaroof.retrofit.manager

import com.kuma.kangaroof.retrofit.BASE_URL
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object HttpManager {
    private val mRetrofit: Retrofit

    init {
        mRetrofit = Retrofit.Builder()
            .client(initOkhttpClient())
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    private fun initOkhttpClient(): OkHttpClient {

        val build = OkHttpClient.Builder()
        return build.build()

    }

}

