package com.joaoferreira.data.services

import com.google.gson.GsonBuilder
import com.joaoferreira.domain.utils.BASE_URL
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val service: MaliApi by lazy {
    Retrofit.Builder().baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
        .build()
        .create(MaliApi::class.java)
}