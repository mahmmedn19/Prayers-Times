/*
 * *
 *  * Created by Mohamed Naser on 6 , 2023.
 *  *
 *  * Copyright (c) 2023 All rights reserved.
 *  *
 *  * Last modified: 6/17/23, 9:30 PM
 *
 *
 */

package com.example.prayerstimes.ui.prayer

import com.example.prayerstimes.R
import com.example.prayerstimes.ui.base.BaseAdapter
import com.example.prayerstimes.ui.base.BaseInteractionListener
import com.example.prayerstimes.ui.uiState.PrayerUiState

class PrayerTimeAdapter(listener: PrayerTimeListener) : BaseAdapter<PrayerUiState>(listener) {
    override val layoutID = R.layout.item_prayer
}

interface PrayerTimeListener : BaseInteractionListener {
    fun onClickPrayer(prayerTime: Long)
}