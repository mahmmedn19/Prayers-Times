package com.example.prayerstimes.di

import com.example.prayerstimes.data.PrayerRepositoryImp
import com.example.prayerstimes.data.local.dao.PrayerTimesDao
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
        prayerService: PrayerService,
        prayerTimesDao: PrayerTimesDao
    ): PrayerRepository {
        return PrayerRepositoryImp(prayerService,prayerTimesDao)
    }
}