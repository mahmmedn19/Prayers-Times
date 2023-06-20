package com.example.prayerstimes.ui.qiblah

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.hardware.Sensor
import android.hardware.SensorManager
import android.location.Geocoder
import android.util.Log
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat.getSystemService
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.lifecycleScope
import com.example.prayerstimes.R
import com.example.prayerstimes.databinding.FragmentQiblahBinding
import com.example.prayerstimes.ui.base.BaseFragment
import com.example.prayerstimes.ui.prayer.PrayerTime
import com.example.prayerstimes.util.DateUtils
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import java.io.IOException
import java.util.Locale

@AndroidEntryPoint
class Qiblah : BaseFragment<FragmentQiblahBinding>() {
    override val TAG: String = this::class.java.simpleName
    override val layoutIdFragment = R.layout.fragment_qiblah
    override val viewModel: QiblahViewModel by viewModels()
    private lateinit var fusedLocationClient: FusedLocationProviderClient


    override fun setup() {
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireActivity())
        getCurrentLocation()
    }

    private fun fetchQiblaDirection(latitude: Double, longitude: Double) {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.fetchQiblaDirection(latitude, longitude)
        }
    }

    private fun getCurrentLocation() {
        if (checkLocationPermission()) {
            fusedLocationClient.lastLocation.addOnSuccessListener { location ->
                location?.let {
                    binding.textLocation.text =
                        getLocationName(location.latitude, location.longitude)
                    fetchQiblaDirection(
                        25.4106386,
                        51.1846025
                    )
                }
            }
        } else {
            requestLocationPermission()
        }
    }

    private fun checkLocationPermission(): Boolean {
        return ActivityCompat.checkSelfPermission(
            requireActivity(), Manifest.permission.ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED
    }

    private fun requestLocationPermission() {
        ActivityCompat.requestPermissions(
            requireActivity(), arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
            REQUEST_LOCATION_PERMISSION
        )
    }

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

    companion object {
        private const val REQUEST_LOCATION_PERMISSION = 1
    }
}