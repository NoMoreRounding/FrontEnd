package com.example.nomorerounding

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import okhttp3.Interceptor

import okhttp3.OkHttpClient
import okhttp3.Request


object User {
    private val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl("http://146.56.158.121:1998/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val server: API = retrofit.create(API::class.java)
}