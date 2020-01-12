package com.retar.go4lunch

import android.Manifest
import android.content.pm.PackageManager
import android.location.Location
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.*
import com.google.android.gms.maps.model.LatLng

class MainActivity : AppCompatActivity(), MainView, OnMapReadyCallback {

    private lateinit var map: GoogleMap


    override fun onMapReady(map: GoogleMap?) {
        map?.let {
            this.map = it
            getLastLocationTest()
        }

    }

    override fun loadMap() {

    }

    private fun loadMapTest() {
        val mapFragment = fragmentManager
            .findFragmentById(R.id.map) as MapFragment
        mapFragment.getMapAsync(this)

    }

    private fun getLastLocationTest(){
       val fusedLocationClient: FusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this)


        fusedLocationClient.lastLocation
            .addOnSuccessListener { location: Location? ->
                location?.let {
                    Toast.makeText(this, "bebe", Toast.LENGTH_LONG).show()
                    val latLng = LatLng(it.latitude, it.longitude)
                    map.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15.0f))

                }
            }
    }



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
            != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                ),
                15
            )
        } else {
            loadMapTest()

        }


    }
}
