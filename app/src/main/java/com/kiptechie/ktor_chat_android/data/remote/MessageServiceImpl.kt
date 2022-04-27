package com.kiptechie.ktor_chat_android.data.remote

import com.kiptechie.ktor_chat_android.data.remote.dto.MessageDto
import com.kiptechie.ktor_chat_android.domain.model.Message
import io.ktor.client.*
import io.ktor.client.request.*

class MessageServiceImpl(
    private val httpClient: HttpClient
) : MessageService {
    override suspend fun getAllMessages(): List<Message> {
        return try {
            httpClient.get<List<MessageDto>>(MessageService.Endpoints.GetAllMessages.url)
                .map {
                    it.toMessage()
                }
        } catch (e: Exception) {
            emptyList()
        }
    }
}