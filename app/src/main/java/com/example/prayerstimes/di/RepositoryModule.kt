package com.example.prayerstimes.di

import com.example.prayerstimes.data.PrayerRepositoryImp
import com.example.prayerstimes.data.remote.network.PrayerService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    fun providePrayerRepo(
        prayerService: PrayerService
    ): PrayerRepositoryImp {
        return PrayerRepositoryImp(prayerService)
    }
}