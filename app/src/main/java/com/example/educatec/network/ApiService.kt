package com.example.educatec.network

import com.example.educatec.model.Usuario
import com.example.educatec.network.request.LoginRequest
import com.example.educatec.network.request.RegisterRequest
import retrofit2.http.Body
import retrofit2.http.POST

interface ApiService {

    @POST("aut/usuarios/login")
    suspend fun login(@Body request: LoginRequest): Usuario

    @POST("aut/usuarios")
    suspend fun register(@Body request: RegisterRequest): String
}