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

import android.widget.Toast
import androidx.fragment.app.viewModels
import com.example.prayerstimes.R
import com.example.prayerstimes.databinding.FragmentPrayerTimeBinding
import com.example.prayerstimes.ui.base.BaseFragment
import com.example.prayerstimes.ui.uiState.PrayerTimesUiState
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PrayerTime : BaseFragment<FragmentPrayerTimeBinding>() {
    override val TAG: String = this::class.java.simpleName
    override val layoutIdFragment = R.layout.fragment_prayer_time
    override val viewModel: PrayerTimeViewModel by viewModels()


    override fun setup() {
        val adapter = PrayerTimeAdapter(viewModel)
        binding.recyclerPrayer.adapter = adapter
        viewModel.prayerTimesUiState.observe(viewLifecycleOwner, ::handlePrayerTimesUiState)
    }

    private fun handlePrayerTimesUiState(uiState: PrayerTimesUiState) {
        val adapter = PrayerTimeAdapter(viewModel)
        when {
            uiState.isLoading -> {
                // Show loading indicator
            }

            uiState.isError -> {
                // Show error message
                Toast.makeText(requireContext(), uiState.message, Toast.LENGTH_SHORT).show()
            }

            uiState.prayerTimes != null -> {
                // Update the adapter data with the prayer times
                adapter.setItems(uiState.prayerTimes)
            }
        }
    }


}