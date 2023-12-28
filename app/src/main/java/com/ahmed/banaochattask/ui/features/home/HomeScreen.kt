package com.ahmed.banaochattask.ui.features.home

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Card
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.ahmed.banaochattask.core.navigation.Screen
import com.ahmed.banaochattask.core.utils.formattedDate
import com.ahmed.banaochattask.data.prefs.LocalPref
import com.ahmed.banaochattask.ui.features.ChatViewModel

@Composable
fun HomeScreen(
    navHostController: NavHostController, chatViewModel: ChatViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    val items = chatViewModel.lastMessagesTimes.collectAsState().value


    LaunchedEffect(key1 = items) {
        chatViewModel.getLastMessagesTimes()

    }
    val id by remember {
        mutableStateOf(LocalPref(context).getString("id")!!)
    }
    Scaffold(floatingActionButton = {
        FloatingActionButton(onClick = {
            LocalPref(context).setString("id", "")
            navHostController.navigate(Screen.AuthScreen.routeName) {
                popUpTo(Screen.HomeScreen.routeName) { inclusive = true }
            }
        }) {
            Icon(Icons.Default.ExitToApp, contentDescription = null)
        }
    }) { padding ->
        LazyColumn(
            Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            items(items.size) { idx ->
                if (items[idx].chat.contains("group") || items[idx].chat.contains(id)) Card(
                    Modifier
                        .padding(5.dp)
                        .clickable {
                            navHostController.navigate(Screen.ChatScreen.routeName)
                            Log.d("item", items[idx].chat)
                            ChatViewModel.loadedChat = items[idx].chat
                        }) {
                    Row(
                        Modifier
                            .fillMaxWidth()
                            .padding(20.dp)
                    ) {
                        Icon(Icons.Default.Person, contentDescription = null)
                        Spacer(modifier = Modifier.width(5.dp))


                        Column {
                            val chatText = remember(items[idx].chat, id) {
                                items[idx].chat.replace(id, "").ifBlank { "group" }
                            }

                            Text(
                                text = "$chatText Chat"
                            )
                            Spacer(modifier = Modifier.height(5.dp))
                            Text(text = formattedDate(items[idx].time))
                        }


                    }
                }


            }
        }

    }

}