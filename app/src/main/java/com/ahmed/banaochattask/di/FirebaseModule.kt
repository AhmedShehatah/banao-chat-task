package com.ahmed.banaochattask.di

import com.ahmed.banaochattask.data.firebase.FireBaseStore
import com.ahmed.banaochattask.data.firebase.FirebaseAuth
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object FirebaseModule {

    @Provides
    @Singleton
    fun provideFirebaseAuth(): FirebaseAuth {
        return FirebaseAuth()
    }

    @Provides
    @Singleton
    fun provideFirebaseDB(): FireBaseStore {
        return FireBaseStore()
    }
}