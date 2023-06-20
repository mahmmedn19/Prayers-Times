package com.example.prayerstimes.data.remote.model

data class QiblahDto(
    val code: Int,
    val status: String,
    val data: QiblahDataDto
)

data class QiblahDataDto(
    val latitude: Double,
    val longitude: Double,
    val direction: Double
)