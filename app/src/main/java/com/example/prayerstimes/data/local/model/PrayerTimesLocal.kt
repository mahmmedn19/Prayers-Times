package com.example.prayerstimes.data.local.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.example.prayerstimes.domain.model.DateEntity
import com.example.prayerstimes.domain.model.TimingsEntity


@Entity(tableName = "prayer_times")
@TypeConverters(TypeConvertersDateTime::class)
data class PrayerTimesLocal(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val timings: TimingsEntity,
    val date: DateEntity
)