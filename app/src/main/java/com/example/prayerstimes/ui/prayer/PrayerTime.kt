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

import android.Manifest.permission.ACCESS_FINE_LOCATION
import android.content.pm.PackageManager
import android.location.Geocoder
import android.util.Log
import androidx.core.app.ActivityCompat
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.prayerstimes.R
import com.example.prayerstimes.databinding.FragmentPrayerTimeBinding
import com.example.prayerstimes.ui.base.BaseFragment
import com.example.prayerstimes.util.DateUtils.getCurrentMonth
import com.example.prayerstimes.util.DateUtils.getCurrentYear
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import java.io.IOException
import java.util.Locale

@AndroidEntryPoint
class PrayerTime : BaseFragment<FragmentPrayerTimeBinding>() {
    override val TAG: String = this::class.java.simpleName
    override val layoutIdFragment = R.layout.fragment_prayer_time
    override val viewModel: PrayerTimeViewModel by viewModels()
    private lateinit var fusedLocationClient: FusedLocationProviderClient

    override fun setup() {
        val adapter = PrayerTimeAdapter(viewModel)
        binding.recyclerPrayer.adapter = adapter
        binding.buttonNext.setOnClickListener {
            navigateToQiblaFragment()
        }
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireActivity())
        getCurrentLocation()

    }

    private fun fetchPrayerTimes(year: Int, month: Int, latitude: Double, longitude: Double) {
        // Launch the coroutine in the lifecycleScope
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.fetchPrayerTimes(year, month, latitude, longitude)
        }
    }


    private fun navigateToQiblaFragment() {
        val action = PrayerTimeDirections.actionPrayerTimeToQiblah()
        findNavController().navigate(action)
    }


    @Suppress("DEPRECATION")
    private fun getLocationName(latitude: Double, longitude: Double): String? {
        val geocoder = Geocoder(requireActivity(), Locale.getDefault())
        try {
            val addresses = geocoder.getFromLocation(latitude, longitude, 1)
            if (addresses!!.isNotEmpty()) {
                val address = addresses[0]
                val sb = StringBuilder()
                for (i in 0..address.maxAddressLineIndex) {
                    sb.append(address.getAddressLine(i)).append("\n")
                }
                return sb.toString()
            }
        } catch (e: IOException) {
            Log.e(TAG, "Error getting location name: ${e.message}")
        }
        return null
    }

    private fun getCurrentLocation() {
        if (checkLocationPermission()) {
            fusedLocationClient.lastLocation.addOnSuccessListener { location ->
                location?.let {
                    val locationName = getLocationName(location.latitude, location.longitude)
                    fetchPrayerTimes(
                        getCurrentYear(),
                        getCurrentMonth(),
                        location.latitude,
                        location.longitude
                    )
                }
            }

        } else {
            requestLocationPermission()
        }
    }

    private fun checkLocationPermission(): Boolean {
        return ActivityCompat.checkSelfPermission(
            requireActivity(), ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED
    }

    private fun requestLocationPermission() {
        ActivityCompat.requestPermissions(
            requireActivity(), arrayOf(ACCESS_FINE_LOCATION), REQUEST_LOCATION_PERMISSION
        )
    }

    companion object {
        const val TAG = "MainActivity"
        private const val REQUEST_LOCATION_PERMISSION = 1
    }
}
