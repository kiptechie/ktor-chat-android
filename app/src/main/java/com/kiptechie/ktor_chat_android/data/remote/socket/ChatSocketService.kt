package com.kiptechie.ktor_chat_android.data.remote.socket

import com.kiptechie.ktor_chat_android.core.util.Resource
import com.kiptechie.ktor_chat_android.domain.model.Message
import kotlinx.coroutines.flow.Flow

interface ChatSocketService {

    companion object {
        const val BASE_URL = "wss://poetcodes.com/ktor-chat"
    }

    sealed class Endpoints(val url: String) {
        object ChatSocket : Endpoints("$BASE_URL/chat-socket")
    }

    suspend fun initSession(
        userName: String
    ): Resource<Unit>

    suspend fun sendMessage(
        message: String
    )

    fun observeMessages(): Flow<Message>

    suspend fun closeSession()
}