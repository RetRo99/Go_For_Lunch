package com.retar.go4lunch.manager.location

import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.location.Location
import androidx.core.app.ActivityCompat
import com.google.android.gms.location.FusedLocationProviderClient
import io.reactivex.subjects.BehaviorSubject

class LocationManager(
    private val fusedLocationClient: FusedLocationProviderClient
) {

    val location: BehaviorSubject<Location> = BehaviorSubject.create()

    init {
        updateLocation()
    }

    @SuppressLint("MissingPermission")
    fun updateLocation() {
        fusedLocationClient.lastLocation
            .addOnSuccessListener { location: Location? ->
                location?.let {
                    this.location.onNext(it)
                }
            }
    }

}
