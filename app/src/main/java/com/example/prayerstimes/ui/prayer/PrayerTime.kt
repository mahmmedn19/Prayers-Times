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
import android.app.AlertDialog
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Geocoder
import android.net.Uri
import android.provider.Settings
import android.util.Log
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.prayerstimes.R
import com.example.prayerstimes.databinding.FragmentPrayerTimeBinding
import com.example.prayerstimes.ui.base.BaseFragment
import com.example.prayerstimes.util.DateUtils.getCurrentDay
import com.example.prayerstimes.util.DateUtils.getCurrentMonth
import com.example.prayerstimes.util.DateUtils.getCurrentYear
import com.example.prayerstimes.util.getCurrentVisiblePosition
import com.example.prayerstimes.util.scrollToPositionSmooth
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
    private val adapter: PrayerTimeAdapter by lazy { PrayerTimeAdapter(viewModel) }

    override fun setup() {
        binding.recyclerPrayer.adapter = adapter

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.prayerDates.collect { prayerDates ->
                updatePrayerDate(prayerDates.firstOrNull())
            }
        }

        binding.buttonNext.setOnClickListener {
            navigateToQiblaFragment()
        }
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireActivity())

        getCurrentLocation()

        binding.btnPrevious.setOnClickListener {
            scrollToPreviousPrayerTime()
        }

        binding.btnNext.setOnClickListener {
            scrollToNextPrayerTime()
        }
        scrollToCurrentDay()
    }

    private fun scrollToCurrentDay() {
        val layoutManager = binding.recyclerPrayer.layoutManager as? LinearLayoutManager
        layoutManager?.let {
            val position = getCurrentDay()
            binding.recyclerPrayer.scrollToPositionSmooth(position)
            updatePrayerDate(viewModel.prayerDates.value.getOrNull(position))
        }
    }

    private fun updatePrayerDate(date: String?) {
        binding.textDate.text = date ?: ""
    }
    private fun fetchPrayerTimes(year: Int, month: Int, latitude: Double, longitude: Double) {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.fetchPrayerTimes(year, month, latitude, longitude)
        }
    }

    private fun navigateToQiblaFragment() {
        val action = PrayerTimeDirections.actionPrayerTimeToQiblah()
        findNavController().navigate(action)
    }

    //region Scroll to position next and previous
    private fun scrollToNextPrayerTime() {
        val layoutManager = binding.recyclerPrayer.layoutManager as? LinearLayoutManager
        layoutManager?.let {
            val currentVisiblePosition = layoutManager.getCurrentVisiblePosition()
            val nextPosition = currentVisiblePosition + 1
            if (nextPosition < adapter.itemCount) {
                binding.recyclerPrayer.scrollToPositionSmooth(nextPosition)
                updatePrayerDate(viewModel.prayerDates.value.getOrNull(nextPosition))
            }
        }
    }

    private fun scrollToPreviousPrayerTime() {
        val layoutManager = binding.recyclerPrayer.layoutManager as? LinearLayoutManager
        layoutManager?.let {
            val currentVisiblePosition = layoutManager.getCurrentVisiblePosition()
            val previousPosition = currentVisiblePosition - 1
            if (previousPosition >= 0) {
                binding.recyclerPrayer.scrollToPositionSmooth(previousPosition)
                updatePrayerDate(viewModel.prayerDates.value.getOrNull(previousPosition))
            }
        }
    }
//endregion Scroll to position next and previous

    //region permissions
    @Suppress("DEPRECATION")
    private fun getLocationName(latitude: Double, longitude: Double): String? {
        val geocoder = Geocoder(requireActivity(), Locale.getDefault())
        try {
            val addresses = geocoder.getFromLocation(latitude, longitude, 1)
            if (addresses != null) {
                if (addresses.isNotEmpty()) {
                    val address = addresses[0]
                    val country = address.countryName
                    val city = address.locality
                    return "$city, $country"
                }
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
                    binding.textLocation.text =
                        getLocationName(location.latitude, location.longitude)
                   // viewModel.fetchPrayerTimesOffline()
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
    @Suppress("DEPRECATION")
    @Deprecated("Deprecated in Java")
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        if (requestCode == REQUEST_LOCATION_PERMISSION) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                getCurrentLocation()
            } else {
                if (ActivityCompat.shouldShowRequestPermissionRationale(
                        requireActivity(),
                        ACCESS_FINE_LOCATION
                    )
                ) {
                    AlertDialog.Builder(requireContext())
                        .setTitle("Location Permission Needed")
                        .setMessage("This app needs the Location permission, please accept to use location functionality.")
                        .setPositiveButton("OK") { _, _ ->
                            requestLocationPermission()
                        }
                        .setNegativeButton("Cancel") { dialog, _ ->
                            Toast.makeText(requireContext(),"This app needs the Location permission",Toast.LENGTH_LONG).show()
                            dialog.dismiss()
                        }
                        .create()
                        .show()
                } else {
                    AlertDialog.Builder(requireContext())
                        .setTitle("Location Permission Needed")
                        .setMessage("This app needs the Location permission. Please go to Settings to grant the permission manually.")
                        .setPositiveButton("OK") { _, _ ->
                            val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
                            val uri = Uri.fromParts("package", "packageName", null)
                            intent.data = uri
                            startActivity(intent)
                        }
                        .setNegativeButton("Cancel") { dialog, _ ->
                            Toast.makeText(requireContext(),"This app needs the Location permission",Toast.LENGTH_LONG).show()
                            dialog.dismiss()
                        }
                        .create()
                        .show()
                }
            }
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
//endregion permissions


    companion object {
        private const val REQUEST_LOCATION_PERMISSION = 1
    }
}
