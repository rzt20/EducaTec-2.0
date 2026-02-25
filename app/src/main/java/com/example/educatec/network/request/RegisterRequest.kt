package com.example.educatec.network.request

data class RegisterRequest(
    val nombre: String,
    val email: String,
    val password: String
)