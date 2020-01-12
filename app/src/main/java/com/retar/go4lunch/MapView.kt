package com.retar.go4lunch


import android.location.Location
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions


class MapView : Fragment(), OnMapReadyCallback {
    private lateinit var googleMap: GoogleMap

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_map_view, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val mapFragment =
            childFragmentManager.findFragmentById(R.id.googleMap) as SupportMapFragment?

        mapFragment?.getMapAsync(this)
    }


    override fun onMapReady(map: GoogleMap?) {
        map?.let {
            googleMap = it
            getLastLocation()
        }
    }

    private fun getLastLocation() {
        activity?.let {
            val fusedLocationClient: FusedLocationProviderClient =
                LocationServices.getFusedLocationProviderClient(it)
            fusedLocationClient.lastLocation
                .addOnSuccessListener { location: Location? ->
                    location?.let {
                        zoomToLocation(location)
                    }
                }
        }


    }

    private fun zoomToLocation(location: Location) {
        Toast.makeText(context, "bebe", Toast.LENGTH_LONG).show()
        val currentLatLng = LatLng(location.latitude, location.longitude)
        googleMap.animateCamera(
            CameraUpdateFactory.newLatLngZoom(
                currentLatLng,
                MainActivity.ZOOM_MODE
            )
        )
        googleMap.addMarker(MarkerOptions().position(currentLatLng))
    }


    companion object {
        @JvmStatic
        fun newInstance() = MapView()
    }
}

