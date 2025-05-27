package com.example.passfort.di

import NetworkCheckerImpl
import android.content.Context
import androidx.room.Room
import com.example.passfort.model.PassFortDB
import com.example.passfort.model.PreferencesManager
import com.example.passfort.repository.AuthRepository
import com.example.passfort.repository.FirebaseAuthRepository
import com.example.passfort.repository.NetworkChecker
import com.example.passfort.repository.PasswordsListRepo
import com.example.passfort.repository.PasswordsListRepoImpl
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext appContext: Context): PassFortDB {
        return Room.databaseBuilder(appContext, PassFortDB::class.java, "PassfortDB").build()
    }

    @Provides
    fun providePasswordsRepository(db: PassFortDB): PasswordsListRepo {
        return PasswordsListRepoImpl(db)
    }

    @Provides
    @Singleton
    fun providePreferencesManager(@ApplicationContext context: Context): PreferencesManager {
        return PreferencesManager(context)
    }

    @Provides
    @Singleton
    fun provideFirebaseAuth(): FirebaseAuth {
        return Firebase.auth
    }

    @Provides
    @Singleton
    fun provideAuthRepository(
        firebaseAuth: FirebaseAuth
    ): AuthRepository {
        return FirebaseAuthRepository(firebaseAuth)
    }


    @Provides
    @Singleton
    fun provideNetworkChecker(@ApplicationContext context: Context): NetworkChecker {
        return NetworkCheckerImpl(context)
    }
}