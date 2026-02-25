package com.example.educatec.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory

object RetrofitClient {

    private val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl("http://20.20.3.168:8080/") // localhost del emulador
        .addConverterFactory(ScalarsConverterFactory.create()) // para recibir String
        .addConverterFactory(GsonConverterFactory.create())    // para recibir JSON
        .build()

    val apiService: ApiService by lazy {
        retrofit.create(ApiService::class.java)
    }
}