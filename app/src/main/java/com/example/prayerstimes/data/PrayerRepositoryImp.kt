/*
 * *
 *  * Created by Mohamed Naser on 6 , 2023.
 *  *
 *  * Copyright (c) 2023 All rights reserved.
 *  *
 *  * Last modified: 6/17/23, 9:45 PM
 *
 *
 */

package com.example.prayerstimes.data

import android.util.Log
import com.example.prayerstimes.data.remote.mapper.toPrayerTimesEntity
import com.example.prayerstimes.data.remote.network.PrayerService
import com.example.prayerstimes.domain.model.PrayerTimesEntity
import com.example.prayerstimes.domain.repo.PrayerRepository
import retrofit2.Response

class PrayerRepositoryImp(private val prayerApi: PrayerService) : PrayerRepository {

    override suspend fun getPrayerTimes(
        year: String,
        month: String,
        latitude: String,
        longitude: String,
        method: String
    ): PrayerTimesEntity? {
        return wrap {
            prayerApi.getPrayerTimes(year, month, latitude, longitude, method)
        }.data.firstOrNull()?.toPrayerTimesEntity()
    }

    private suspend fun <T : Any> wrap(function: suspend () -> Response<T>): T {
        val response = function()
        return if (response.isSuccessful) {
            response.body() ?: throw Throwable("Unknown error occurred")
        } else {
            Log.e("TAG", "wrap: ${response}")
            throw Throwable("Unknown error occurred")
        }
    }

}