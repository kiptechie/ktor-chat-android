package com.kiptechie.ktor_chat_android.domain.model

data class Message(
    val text: String,
    val formattedTime: String,
    val username: String
)