package com.example.prayerstimes.data.remote.model

import com.google.gson.annotations.SerializedName

data class TimingsDto(
    @SerializedName("Fajr")
    val fajr: String,
    @SerializedName("Sunrise")
    val sunrise: String,
    @SerializedName("Dhuhr")
    val dhuhr: String,
    @SerializedName("Asr")
    val asr: String,
    @SerializedName("Maghrib")
    val maghrib: String,
    @SerializedName("Isha")
    val isha: String
)