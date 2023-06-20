package com.example.prayerstimes.data.remote.model

import com.google.gson.annotations.SerializedName

data class PrayerTimesDto(
    @SerializedName("timings")
    val timings: TimingsDto,
    @SerializedName("date")
    val date: DateDto
)


