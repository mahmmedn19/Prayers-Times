package com.example.prayerstimes.data.remote.model

import com.google.gson.annotations.SerializedName

data class DateDto(
    @SerializedName("readable")
    val readable: String,
    @SerializedName("timestamp")
    val timestamp: String,
)