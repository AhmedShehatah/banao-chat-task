@file:OptIn(ExperimentalComposeUiApi::class)

package com.ahmed.banaochattask.ui.features.chat

import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.ahmed.banaochattask.data.prefs.LocalPref
import com.ahmed.banaochattask.ui.features.ChatViewModel
import kotlinx.coroutines.delay

data class ChatMessage(val sender: String, val message: String)

@Composable
fun ChatScreen(chatViewModel: ChatViewModel = hiltViewModel()) {
    val context = LocalContext.current
    val chatMessages = chatViewModel.messages.collectAsState().value

    LaunchedEffect(key1 = true) {
        chatViewModel.loadMessages()
    }
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background),

        ) {
        // Display the chat screen
        ChatBody(messages = chatMessages)

        // Display the input field for sending messages
        ChatInput(Modifier.align(Alignment.BottomCenter)) { newMessage ->
            // Update thea list of messages with the new message
//            chatMessages.add(ChatMessage("You", newMessage))
            chatViewModel.sendMessage(LocalPref(context).getString("id")!!, newMessage)

            chatViewModel.updateLastMessage(newMessage)
        }
    }
}


@Composable
fun ChatBody(messages: List<ChatMessage>) {
    val lazyListState = rememberLazyListState()
    LaunchedEffect(key1 = messages.size) {
        delay(100)
        lazyListState.animateScrollToItem(lazyListState.layoutInfo.totalItemsCount)
    }
    LazyColumn(
        state = lazyListState,
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(16.dp),
        verticalArrangement = Arrangement.Bottom
    ) {
        items(messages) { message ->
            // Display each chat message in a MessageCard
            MessageCard(message = message)
        }
        item { Spacer(modifier = Modifier.height(50.dp)) }


    }


}

@Composable
fun MessageCard(message: ChatMessage) {
    // A basic card to display sender name and message body
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        color = if (isSystemInDarkTheme()) Color.DarkGray else MaterialTheme.colorScheme.primary
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            // Display sender name
            Text(
                text = message.sender, style = MaterialTheme.typography.bodyMedium.copy(
                    color = if (isSystemInDarkTheme()) Color.Gray else Color.Black
                )
            )
            // Display message body
            Text(
                text = message.message,
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier.padding(top = 4.dp)
            )
        }
    }
}

@Composable
fun ChatInput(
    modifier: Modifier = Modifier, onMessageSent: (String) -> Unit
) {
    var message by remember { mutableStateOf("") }


    // Use BasicTextField to capture user input
    TextField(
        value = message,
        onValueChange = {
            message = it
        },
        textStyle = MaterialTheme.typography.bodyMedium,
        keyboardOptions = KeyboardOptions.Default.copy(
            imeAction = ImeAction.Send
        ),
        keyboardActions = KeyboardActions(onSend = {
            onMessageSent(message)
            message = ""

        }),
        modifier = modifier.fillMaxWidth()
//            .padding(16.dp)
    )
}
