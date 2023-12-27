package com.ahmed.banaochattask.data.repos

import com.ahmed.banaochattask.data.models.LastMessagesTime
import com.ahmed.banaochattask.ui.features.chat.ChatMessage
import kotlinx.coroutines.flow.Flow

interface IChatRepo {

    fun getLastMessagesTime(): Flow<List<LastMessagesTime>>
    fun getChatMessages(chat: String): Flow<List<ChatMessage>>
    fun sendMessage(chat: String, sender: String, message: String): Flow<Boolean>
    fun updateLastMessage(chat: String): Flow<Boolean>}