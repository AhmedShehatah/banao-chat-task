package com.ahmed.banaochattask.data.firebase

import android.util.Log
import com.ahmed.banaochattask.core.utils.getCurrentDateTime
import com.ahmed.banaochattask.data.models.LastMessagesTime
import com.ahmed.banaochattask.ui.features.chat.ChatMessage
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow

class FireBaseStore {

    private val db = FirebaseDatabase.getInstance()
    private val ref = db.reference
    fun getLastMessagesTimes(): Flow<List<LastMessagesTime>> = callbackFlow {
        ref.child("last").addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                Log.d("db", "called")
                val data = ArrayList<LastMessagesTime>()
                for (item in snapshot.children) data.add(
                    LastMessagesTime(
                        item.key!!, item.value.toString()
                    )
                )
                trySend(data.toList())

            }

            override fun onCancelled(error: DatabaseError) {
                error(error.message)
            }
        })
        awaitClose { }
    }


    fun sendMessage(chat: String, sender: String, message: String): Flow<Boolean> = callbackFlow {
        ref.child("chat").child(chat).push()
            .setValue(mapOf("sender" to sender, "message" to message)).addOnCompleteListener {
                if (it.isSuccessful) {
                    trySend(true)
                } else {
                    error(false)
                }
            }
        awaitClose {}
    }

    fun getChatMessages(chat: String): Flow<List<ChatMessage>> = callbackFlow {
        Log.d("chat", chat)
        ref.child("chat").child(chat).addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val data = ArrayList<ChatMessage>()
                for (item in snapshot.children) {
                    val message = ChatMessage(
                        item.child("sender").value.toString(),
                        item.child("message").value.toString()
                    )
                    data.add(message)

                }
                trySend(data)
            }

            override fun onCancelled(error: DatabaseError) {
                error(error.message)
            }
        })
        awaitClose {}
    }

    fun updateLastMessage(chat: String): Flow<Boolean> = callbackFlow {
        ref.child("last").updateChildren(mapOf(chat to getCurrentDateTime()))
            .addOnCompleteListener {
                if (it.isSuccessful) trySend(true) else error(false)
            }
        awaitClose {}
    }


}