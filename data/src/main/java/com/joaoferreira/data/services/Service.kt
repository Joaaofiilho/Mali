package com.joaoferreira.data.services

import com.google.gson.GsonBuilder
import com.joaoferreira.domain.utils.BASE_URL
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

fun getService(): MaliApi =
    Retrofit.Builder().baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
        .client(OkHttpClient())
        .build()
        .create(MaliApi::class.java)