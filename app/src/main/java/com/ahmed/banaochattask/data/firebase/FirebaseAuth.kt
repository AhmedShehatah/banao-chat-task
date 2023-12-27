package com.ahmed.banaochattask.data.firebase

import com.google.firebase.Firebase
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.flow

class FirebaseAuth {
    private val auth: FirebaseAuth = Firebase.auth

    fun signIn(email: String, password: String): Flow<String> = callbackFlow {
        auth.signInWithEmailAndPassword(email, password).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                trySend(task.result.user!!.uid)
            } else {
                error(task.result.toString())
            }

        }

        awaitClose { }
    }

    fun signOut(): Flow<Unit> = callbackFlow {
        auth.signOut()
        trySend(Unit)
        awaitClose { }
    }

}