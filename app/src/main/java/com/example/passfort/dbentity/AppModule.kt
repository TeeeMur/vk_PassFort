package com.example.passfort.dbentity

import android.content.Context
import androidx.room.Room
import com.example.passfort.model.PassFortDB
import com.example.passfort.repository.PasswordsRepoImpl
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
    fun providePasswordsRepository(db: PassFortDB): PasswordsRepoImpl {
        return PasswordsRepoImpl(db)
    }
}