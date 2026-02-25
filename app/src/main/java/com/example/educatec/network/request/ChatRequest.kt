package com.example.educatec.network.request

import com.google.gson.annotations.SerializedName

data class ChatRequest(
    val model: String,
    val messages: List<ChatMessage>,
    @SerializedName("max_tokens") val maxTokens: Int
)

data class ChatMessage(
    val role: String,
    val content: String
)