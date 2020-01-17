package com.retar.go4lunch.ui.map


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
import com.retar.go4lunch.MainActivity
import com.retar.go4lunch.R
import com.retar.go4lunch.ui.map.model.UiMarkerModel
import kotlinx.android.synthetic.main.fragment_map_view.*


class MapFragment : Fragment(), MapView,
    OnMapReadyCallback {
    private lateinit var googleMap: GoogleMap

    val presenter = MapViewPresenterImpl(this)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_map_view, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        fabGetLocation.setOnClickListener {
            presenter.zoomToCurrentLocation()
        }

        presenter.onActivityCreated()

    }

    override fun onMapReady(map: GoogleMap?) {
        map?.let {
            googleMap = it

            googleMap.setOnInfoWindowClickListener { marker ->
                Toast.makeText(context, marker.tag.toString(), Toast.LENGTH_LONG).show()
            }

            presenter.onMapReady()
        }
    }

    override fun getMapAsync() {
        val mapFragment =
            childFragmentManager.findFragmentById(R.id.googleMap) as SupportMapFragment?

        mapFragment?.getMapAsync(this)

    }

    override fun getLastLocation() {
        activity?.let {
            val fusedLocationClient: FusedLocationProviderClient =
                LocationServices.getFusedLocationProviderClient(it)
            fusedLocationClient.lastLocation
                .addOnSuccessListener { location: Location? ->
                    location?.let {
                        presenter.onGotLastLocation(location)
                    }
                }
        }

    }

    override fun zoomToLocation(latLng: LatLng) {
        googleMap.animateCamera(
            CameraUpdateFactory.newLatLngZoom(
                latLng,
                MainActivity.ZOOM_MODE
            )
        )
        googleMap.addMarker(MarkerOptions().position(latLng))
    }

    override fun addMarker(marker: UiMarkerModel) {
        googleMap.addMarker(MarkerOptions().position(marker.latLng).title(marker.title)).tag =
            marker.id

    }


    companion object {
        @JvmStatic
        fun newInstance() = MapFragment()
    }

}

