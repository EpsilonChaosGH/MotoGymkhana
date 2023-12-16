package com.example.motogymkhana.di

import com.example.motogymkhana.data.network.StagesService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ServicesModule {


    @Provides
    @Singleton
    fun providesStagesService(retrofit: Retrofit): StagesService {
        return retrofit.create(StagesService::class.java)
    }
}