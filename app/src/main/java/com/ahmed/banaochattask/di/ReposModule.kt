package com.ahmed.banaochattask.di

import com.ahmed.banaochattask.data.firebase.FireBaseStore
import com.ahmed.banaochattask.data.firebase.FirebaseAuth
import com.ahmed.banaochattask.data.repos.AuthRepo
import com.ahmed.banaochattask.data.repos.ChatRepo
import com.ahmed.banaochattask.data.repos.IAuthRepo
import com.ahmed.banaochattask.data.repos.IChatRepo
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ReposModule {

    @Provides
    @Singleton
    fun provideAuthRepo(firebaseAuth: FirebaseAuth): IAuthRepo {
        return AuthRepo(firebaseAuth)
    }

    @Provides
    @Singleton
    fun provideChatRepo(db: FireBaseStore): IChatRepo {
        return ChatRepo(db)
    }
}