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

import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModel
import com.example.prayerstimes.R
import com.example.prayerstimes.databinding.FragmentPrayerTimeBinding
import com.example.prayerstimes.ui.base.BaseFragment

class PrayerTime : BaseFragment<FragmentPrayerTimeBinding>() {
    override val TAG: String = this::class.java.simpleName
    override val layoutIdFragment = R.layout.fragment_prayer_time
    override val viewModel: ViewModel by viewModels()
    private val adapter: PrayerTimeAdapter by lazy { PrayerTimeAdapter(viewModel) }

    override fun setup() {
        binding.recyclerPrayer.adapter = adapter
    }
}