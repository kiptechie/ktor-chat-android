package com.kiptechie.ktor_chat_android.presentation.chat

import com.kiptechie.ktor_chat_android.domain.model.Message

data class ChatState(
    val messages: List<Message> = emptyList(),
    val isLoading: Boolean = false
)
