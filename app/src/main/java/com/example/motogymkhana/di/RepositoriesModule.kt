package com.example.motogymkhana.di


import com.example.motogymkhana.data.StageRepository
import com.example.motogymkhana.data.StageRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoriesModule {

    @Binds
    abstract fun bindStageRepository(
        stageRepositoryImpl: StageRepositoryImpl
    ): StageRepository

}