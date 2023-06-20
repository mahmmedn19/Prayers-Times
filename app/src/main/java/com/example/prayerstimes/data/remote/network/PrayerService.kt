/*
 * *
 *  * Created by Mohamed Naser on 6 , 2023.
 *  *
 *  * Copyright (c) 2023 All rights reserved.
 *  *
 *  * Last modified: 6/17/23, 9:49 PM
 *
 *
 */

package com.example.prayerstimes.data.remote.network

import com.example.prayerstimes.data.remote.model.PrayerTimesResponse
import com.example.prayerstimes.data.remote.model.QiblahDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface PrayerService {
    @GET("{year}/{month}")
    suspend fun getPrayerTimes(
        @Path("year") year: Int,
        @Path("month") month: Int,
        @Query("latitude") latitude: Double,
        @Query("longitude") longitude: Double,
        @Query("method") method: Int
    ): Response<PrayerTimesResponse>

    @GET("v1/qibla/{latitude}/{longitude}")
    suspend fun getQiblaDirection(
        @Path("latitude") latitude: Double,
        @Path("longitude") longitude: Double
    ): Response<QiblahDto>
}