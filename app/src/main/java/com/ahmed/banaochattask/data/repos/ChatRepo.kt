package com.ahmed.banaochattask.data.repos

import com.ahmed.banaochattask.data.firebase.FireBaseStore
import com.ahmed.banaochattask.data.models.LastMessagesTime
import com.ahmed.banaochattask.ui.features.chat.ChatMessage
import kotlinx.coroutines.flow.Flow

class ChatRepo(private val db: FireBaseStore) : IChatRepo {
    override fun getLastMessagesTime(): Flow<List<LastMessagesTime>> = db.getLastMessagesTimes()
    override fun getChatMessages(chat: String): Flow<List<ChatMessage>> = db.getChatMessages(chat)

    override fun sendMessage(chat: String, sender: String, message: String): Flow<Boolean> =
        db.sendMessage(chat, sender, message)

    override fun updateLastMessage(chat: String): Flow<Boolean> = db.updateLastMessage(chat)
}