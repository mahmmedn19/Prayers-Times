package com.example.prayerstimes.data.remote.mapper

import com.example.prayerstimes.data.remote.model.PrayerTimesDto
import com.example.prayerstimes.domain.model.DateEntity
import com.example.prayerstimes.domain.model.PrayerTimesEntity
import com.example.prayerstimes.domain.model.TimingsEntity

fun PrayerTimesDto.toPrayerTimesEntity(): PrayerTimesEntity {
    val timings = TimingsEntity(
        fajr = timings.fajr,
        sunrise = timings.sunrise,
        dhuhr = timings.dhuhr,
        asr = timings.asr,
        maghrib = timings.maghrib,
        isha = timings.isha
    )
    val date = DateEntity(
        readable = date.readable,
        timestamp = date.timestamp
    )
    return PrayerTimesEntity(
        timings,
        date
    )
}