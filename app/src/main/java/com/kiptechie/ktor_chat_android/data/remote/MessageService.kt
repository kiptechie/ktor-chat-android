package com.kiptechie.ktor_chat_android.data.remote

import com.kiptechie.ktor_chat_android.domain.model.Message

interface MessageService {

    suspend fun getAllMessages(): List<Message>

    companion object {
        const val BASE_URL = "https://poetcodes.com/ktor-chat"
    }

    sealed class Endpoints(val url: String) {
        object GetAllMessages : Endpoints("$BASE_URL/messages")
    }
}