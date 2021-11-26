package com.kiptechie.ktor_chat_android.data.remote.socket

import android.util.Log
import com.kiptechie.ktor_chat_android.core.util.Resource
import com.kiptechie.ktor_chat_android.data.remote.dto.MessageDto
import com.kiptechie.ktor_chat_android.domain.model.Message
import io.ktor.client.*
import io.ktor.client.features.websocket.*
import io.ktor.client.request.*
import io.ktor.http.cio.websocket.*
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.isActive
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json

class ChatSocketServiceImpl(
    private val httpClient: HttpClient
) : ChatSocketService {

    private var socket: WebSocketSession? = null

    companion object {
        const val TAG = "ChatSocketServiceImpl"
    }

    override suspend fun initSession(userName: String): Resource<Unit> {
        return try {
            socket = httpClient.webSocketSession {
                url("${ChatSocketService.Endpoints.ChatSocket.url}?username=$userName")
            }
            if (socket?.isActive == true) {
                Resource.Success(Unit)
            } else {
                Resource.Error(message = "Could not establish a connection!")
            }
        } catch (e: Exception) {
            Log.e(TAG, e.message, e)
            Resource.Error(e.message ?: "Unknown error")
        }
    }

    override suspend fun sendMessage(message: String) {
        try {
            socket?.send(Frame.Text(message))
        } catch (e: Exception) {
            Log.e(TAG, e.message, e)
        }
    }

    override fun observeMessages(): Flow<Message> {
        return try {
            socket?.incoming?.receiveAsFlow()?.filter { it is Frame.Text }
                ?.map {
                    val json = (it as? Frame.Text)?.readText() ?: ""
                    val messageDto = Json.decodeFromString<MessageDto>(json)
                    messageDto.toMessage()
                } ?: flow { }
        } catch (e: Exception) {
            Log.e(TAG, e.message, e)
            return flow { }
        }
    }

    override suspend fun closeSession() {
        try {
            socket?.close()
        } catch (e: Exception) {
            Log.e(TAG, e.message, e)
        }
    }
}