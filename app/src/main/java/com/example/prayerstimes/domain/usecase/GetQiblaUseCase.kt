/*
 * *
 *  * Created by Mohamed Naser on 6 , 2023.
 *  *
 *  * Copyright (c) 2023 All rights reserved.
 *  *
 *
 *
 *
 */
package com.example.prayerstimes.domain.usecase

import com.example.prayerstimes.domain.repo.PrayerRepository
import javax.inject.Inject

class GetQiblaUseCase @Inject constructor(
    private val prayerRepository: PrayerRepository
) {

    suspend operator fun invoke(
         latitude: Double, longitude: Double
    ): Double {
        return prayerRepository.getQiblaDirection(latitude, longitude)
    }

}