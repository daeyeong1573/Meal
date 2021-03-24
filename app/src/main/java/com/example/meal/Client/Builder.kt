package com.example.meal.Client

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object Builder {
    var api : API

    init {
        val retrofit = Retrofit.Builder()
                .baseUrl("https://open.neis.go.kr/")
                .addConverterFactory(GsonConverterFactory.create())
                .client(createOkHttpClient())
                .build()
        api = retrofit.create(API::class.java)
    }

    private fun createOkHttpClient(): OkHttpClient {
        val builder = OkHttpClient.Builder()
        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY
        builder.addInterceptor(interceptor)
        return builder.build()
    }

}