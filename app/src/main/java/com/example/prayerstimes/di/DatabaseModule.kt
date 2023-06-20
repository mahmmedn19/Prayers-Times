package com.example.prayerstimes.di

import android.content.Context
import androidx.room.Room
import com.example.prayerstimes.data.local.PrayerDatabase
import com.example.prayerstimes.data.local.dao.PrayerTimesDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

        @Singleton
        @Provides
        fun provideRoomDatabase(@ApplicationContext context: Context): PrayerDatabase =
            Room.databaseBuilder(context, PrayerDatabase::class.java, "prayer_times_db").build()

        @Singleton
        @Provides
        fun provideMarvelDao(prayerDataBase: PrayerDatabase): PrayerTimesDao {
            return prayerDataBase.prayerTimesDao()
        }


}
