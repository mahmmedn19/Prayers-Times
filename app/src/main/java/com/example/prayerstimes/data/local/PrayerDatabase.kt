package com.example.prayerstimes.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.prayerstimes.data.local.dao.PrayerTimesDao
import com.example.prayerstimes.data.local.model.PrayerTimesLocal

@Database(
    entities = [
        PrayerTimesLocal::class,
    ],
    version = 1
)
abstract class PrayerDatabase : RoomDatabase() {
    abstract fun prayerTimesDao(): PrayerTimesDao
}