package com.example.motogymkhana.di


import com.example.motogymkhana.data.GymkhanaCupRepository
import com.example.motogymkhana.data.GymkhanaCupRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoriesModule {

    @Binds
    abstract fun bindStageRepository(
        stageRepositoryImpl: GymkhanaCupRepositoryImpl
    ): GymkhanaCupRepository

}