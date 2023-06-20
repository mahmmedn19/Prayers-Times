package com.example.prayerstimes.data.local.mapper

import com.example.prayerstimes.data.local.model.PrayerTimesLocal
import com.example.prayerstimes.domain.model.DateEntity
import com.example.prayerstimes.domain.model.PrayerTimesEntity
import com.example.prayerstimes.domain.model.TimingsEntity

fun PrayerTimesEntity.toPrayerTimesLocal(): PrayerTimesLocal {
    val timingsLocal = timings.toTimingsLocal()
    val dateLocal = date.toDateLocal()
    return PrayerTimesLocal(0, timingsLocal, dateLocal)
}

fun TimingsEntity.toTimingsLocal(): TimingsEntity {
    return TimingsEntity(fajr, sunrise, dhuhr, asr, maghrib, isha)
}

fun DateEntity.toDateLocal(): DateEntity {
    return DateEntity(readable, timestamp)
}