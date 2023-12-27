package com.ahmed.banaochattask.data.repos

import com.ahmed.banaochattask.data.firebase.FirebaseAuth
import kotlinx.coroutines.flow.Flow

class AuthRepo(private val firebaseAuth: FirebaseAuth) : IAuthRepo {

    override fun signIn(email: String, password: String): Flow<String> =
        firebaseAuth.signIn(email, password)

    override fun signOut(): Flow<Unit> = firebaseAuth.signOut()
}