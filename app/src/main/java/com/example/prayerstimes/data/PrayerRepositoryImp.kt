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
import com.example.prayerstimes.data.local.dao.PrayerTimesDao
import com.example.prayerstimes.data.local.mapper.toPrayerTimesLocal
import com.example.prayerstimes.data.remote.mapper.toPrayerTimesEntity
import com.example.prayerstimes.data.remote.network.PrayerService
import com.example.prayerstimes.domain.model.TimingsEntity
import com.example.prayerstimes.domain.repo.PrayerRepository
import retrofit2.Response
import javax.inject.Inject

class PrayerRepositoryImp @Inject constructor(
    private val prayerApi: PrayerService,
    private val prayerTimesDao: PrayerTimesDao
) : PrayerRepository {

    override suspend fun getPrayerTimes(
        year: Int,
        month: Int,
        latitude: Double,
        longitude: Double,
        method: Int
    ): List<TimingsEntity> {
            val prayerTimesResponse = wrap {
                prayerApi.getPrayerTimes(year, month, latitude, longitude, method)
            }

            val prayerTimesDtoList = prayerTimesResponse.data
            val prayerTimesEntityList = prayerTimesDtoList.map { it.toPrayerTimesEntity() }
            val timingsEntityList = prayerTimesEntityList.map { it.timings }

            val prayerTimesLocalList = prayerTimesEntityList.map { it.toPrayerTimesLocal() }
            prayerTimesDao.insertPrayerTimes(prayerTimesLocalList)
            return timingsEntityList

    }
    override suspend fun getPrayerTimesOffline(): List<TimingsEntity> {
        return prayerTimesDao.getPrayerTimes().map { it.timings }
    }

    override suspend fun getQiblaDirection(latitude: Double, longitude: Double): Double {
       return wrap { prayerApi.getQiblaDirection(latitude, longitude) }.data.direction
    }


    override suspend fun getPrayerDate(
        year: Int, month: Int, latitude: Double, longitude: Double, method: Int
    ): List<String> {
        return wrap {
            prayerApi.getPrayerTimes(year, month, latitude, longitude, method)
        }.data.map { it.toPrayerTimesEntity().date.readable }
    }

    private suspend fun <T : Any> wrap(function: suspend () -> Response<T>): T {
        val response = function()
        return if (response.isSuccessful) {
            response.body() ?: throw Throwable("Unknown error occurred")
        } else {
            Log.e("TAG", "wrap: $response")
            throw Throwable("Unknown error occurred")
        }
    }

}