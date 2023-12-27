package com.ahmed.banaochattask.ui.features

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ahmed.banaochattask.core.utils.sortLatestMessages
import com.ahmed.banaochattask.data.models.LastMessagesTime
import com.ahmed.banaochattask.data.repos.IChatRepo
import com.ahmed.banaochattask.ui.features.chat.ChatMessage
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ChatViewModel @Inject constructor(private val repo: IChatRepo) : ViewModel() {

    init {
        getLastMessagesTimes()

    }

    private val _lastMessagesTimes = MutableStateFlow(emptyList<LastMessagesTime>())

    val lastMessagesTimes: StateFlow<List<LastMessagesTime>> = _lastMessagesTimes

    private fun getLastMessagesTimes() {

        viewModelScope.launch {
            repo.getLastMessagesTime().collect { items ->
                val sortedList = sortLatestMessages(items)
                _lastMessagesTimes.value = sortedList
            }

        }

    }

    private val _messages = MutableStateFlow<List<ChatMessage>>(emptyList())
    val messages: StateFlow<List<ChatMessage>> = _messages
    fun loadMessages() {
        viewModelScope.launch {
            repo.getChatMessages(loadedChat).collect { _messages.value = it }
        }
    }

    fun sendMessage(sender: String, message: String) {
        viewModelScope.launch {
            repo.sendMessage(loadedChat, sender, message).collect {}
        }
    }

    fun updateLastMessage() {
        viewModelScope.launch {
            repo.updateLastMessage(loadedChat).collect {}
        }
    }

    companion object {
        var loadedChat = ""
    }
}