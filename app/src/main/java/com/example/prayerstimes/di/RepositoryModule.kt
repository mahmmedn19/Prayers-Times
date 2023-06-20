package com.example.prayerstimes.di

import com.example.prayerstimes.data.PrayerRepositoryImp
import com.example.prayerstimes.data.remote.network.PrayerService
import com.example.prayerstimes.domain.repo.PrayerRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Singleton
    @Provides
    fun providePrayerRepo(
        prayerService: PrayerService
    ): PrayerRepository {
        return PrayerRepositoryImp(prayerService)
    }
}