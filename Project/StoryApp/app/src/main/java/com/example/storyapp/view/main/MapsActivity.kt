package com.example.storyapp.view.main

import android.Manifest
import android.content.pm.PackageManager
import android.location.Geocoder
import android.os.Bundle
import android.util.Log
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import com.example.storyapp.R
import com.example.storyapp.databinding.ActivityMapsBinding
import com.example.storyapp.view.MainViewModelFactory
import com.example.storyapp.view.model.MainViewModel
import com.example.storyapp.view.response.ListStoryItem
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.LatLngBounds
import com.google.android.gms.maps.model.MarkerOptions
import java.io.IOException
import java.util.*

class MapsActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap
    private lateinit var binding: ActivityMapsBinding
    private lateinit var myViewModel: MainViewModel
    private val boundsBuilder = LatLngBounds.Builder()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMapsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val mapFragment = supportFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        val factory = MainViewModelFactory(getSharedPreferences("prefs", MODE_PRIVATE), this)
        myViewModel = ViewModelProvider(this, factory)[MainViewModel::class.java]

        myViewModel.getDataWithLocation(1)
        myViewModel.itemsWithLoc.observe(this) { item ->
            setItemData(item)
        }
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        mMap.uiSettings.isZoomControlsEnabled = true
        mMap.uiSettings.isIndoorLevelPickerEnabled = true
        mMap.uiSettings.isCompassEnabled = true
        mMap.uiSettings.isMapToolbarEnabled = true
        getMyLocation()
    }

    private val requestPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted: Boolean ->
        if (isGranted) {
            getMyLocation()
        }
    }

    private fun getMyLocation() {
        if (ContextCompat.checkSelfPermission(
                this.applicationContext,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            mMap.isMyLocationEnabled = true
        } else {
            requestPermissionLauncher.launch(
                Manifest.permission.ACCESS_FINE_LOCATION
            )
        }
    }

    private fun setItemData(item: List<ListStoryItem>) {
        item.forEach { data ->
//            jika data lat lebih dari 90 atau kurang dari -90 maka jangan masukkan ke marker
            if (data.lat.toDouble() > 90 || data.lat.toDouble() < -90) {
                return@forEach
            } else {
                val lat = data.lat
                val lon = data.lon
                val location = LatLng(lat.toDouble(), lon.toDouble())
                val address = getAddressName(data.lat.toDouble(), data.lon.toDouble())
                val marker = mMap.addMarker(
                    MarkerOptions().position(location).title(data.name)
                        .snippet("$address")
                )
                marker?.let {
                    boundsBuilder.include(it.position)
                }
            }
        }
        val bounds = boundsBuilder.build()
        val cameraUpdate = CameraUpdateFactory.newLatLngBounds(bounds, 300)
        mMap.animateCamera(cameraUpdate)
    }

    private fun getAddressName(lat: Double, lon: Double): String? {
        var addressName: String? = null
        val geocoder = Geocoder(this@MapsActivity, Locale.getDefault())
        try {
            val list = geocoder.getFromLocation(lat, lon, 1)
            if (list != null && list.isNotEmpty()) {
                addressName = list[0].getAddressLine(0)
                Log.d("address", "getAddressName: $addressName")
            }
        } catch (e: IOException) {
            e.printStackTrace()
            return null
        }
        return addressName
    }

}