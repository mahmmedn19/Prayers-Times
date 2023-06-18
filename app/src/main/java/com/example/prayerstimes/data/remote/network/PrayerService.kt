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
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface PrayerService {
    @GET("{year}/{month}")
    suspend fun getPrayerTimes(
        @Path("year") year: String,
        @Path("month") month: String,
        @Query("latitude") latitude: String,
        @Query("longitude") longitude: String,
        @Query("method") method: String
    ): Response<PrayerTimesResponse>
}