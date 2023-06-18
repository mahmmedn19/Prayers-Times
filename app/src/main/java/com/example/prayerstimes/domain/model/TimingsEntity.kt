package com.example.prayerstimes.domain.model

import com.google.gson.annotations.SerializedName

data class TimingsEntity (
    val fajr: String,
    val Sunrise: String,
    val dhuhr: String,
    val asr: String,
    val maghrib: String,
    val isha: String
)
