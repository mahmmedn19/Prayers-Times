package com.example.prayerstimes.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.prayerstimes.data.local.model.PrayerTimesLocal

@Dao
interface PrayerTimesDao {
    @Query("SELECT * FROM prayer_times")
    suspend fun getPrayerTimes(): List<PrayerTimesLocal>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPrayerTimes(prayerTimes: List<PrayerTimesLocal>)
}