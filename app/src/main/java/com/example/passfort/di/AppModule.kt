package com.example.passfort.di

import android.content.Context
import androidx.room.Room
import com.example.passfort.model.PassFortDB
import com.example.passfort.repository.MainScreenRepo
import com.example.passfort.repository.PasswordsListRepo
import com.example.passfort.repository.impl.MainScreenRepoImpl
import com.example.passfort.repository.impl.PasswordsListRepoImpl
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
        return Room
            .databaseBuilder(appContext, PassFortDB::class.java , "PassfortDB")
            .fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    fun providePasswordsRepository(db: PassFortDB): PasswordsListRepo {
        return PasswordsListRepoImpl(db)
    }

    @Provides
    fun provideMainPasswordsRepository(db: PassFortDB): MainScreenRepo {
        return MainScreenRepoImpl(db)
    }
}