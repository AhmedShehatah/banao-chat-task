package com.ahmed.banaochattask.data.repos

import kotlinx.coroutines.flow.Flow

interface IAuthRepo {

    fun signIn(email: String, password: String): Flow<String>
    fun signOut(): Flow<Unit>


}