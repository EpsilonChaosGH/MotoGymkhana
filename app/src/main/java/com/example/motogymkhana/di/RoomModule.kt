package com.example.motogymkhana.di

import android.content.Context
import androidx.room.Room
import com.example.motogymkhana.data.local.AppDatabase
import com.example.motogymkhana.data.local.FavoritesDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RoomModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): AppDatabase =
        Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "WeatherApp-DB"
        )
            .build()


    @Provides
    @Singleton
    fun favoritesDao(db: AppDatabase): FavoritesDao = db.favoritesDao()
}