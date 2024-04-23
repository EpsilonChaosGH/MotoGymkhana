package com.example.motogymkhana.di

import android.content.Context
import com.example.motogymkhana.screens.settings.SettingsSharedPref
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object SettingsModule {

    @Provides
    @Singleton
    fun providesSettingsSharedPref(@ApplicationContext context: Context): SettingsSharedPref {
        return SettingsSharedPref(context)
    }
}