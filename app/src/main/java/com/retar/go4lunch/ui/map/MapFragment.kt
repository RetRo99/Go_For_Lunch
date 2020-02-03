package com.retar.go4lunch.ui.map


import android.location.Location
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MapStyleOptions
import com.google.android.gms.maps.model.MarkerOptions
import com.retar.go4lunch.R
import com.retar.go4lunch.ui.map.model.UiMarkerModel
import dagger.android.support.DaggerFragment
import kotlinx.android.synthetic.main.fragment_map_view.*
import javax.inject.Inject


class MapFragment : DaggerFragment(), MapView,
    OnMapReadyCallback {
    private lateinit var googleMap: GoogleMap
    private var mapFragment: SupportMapFragment? = null

    @Inject
    lateinit var presenter: MapViewPresenter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_map_view, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        fabGetLocation.setOnClickListener {
            presenter.onFabClick()
        }

        presenter.onActivityCreated()

    }

    override fun onMapReady(map: GoogleMap?) {
        map?.let {
            googleMap = it
            presenter.onMapReady()
        }
    }

    override fun getMapAsync() {

        if (mapFragment == null) {
            mapFragment =
                childFragmentManager.findFragmentById(R.id.googleMap) as SupportMapFragment?
            mapFragment?.getMapAsync(this)
        }

    }

    override fun getLastLocation(isFromFab: Boolean) {
        activity?.let {
            val fusedLocationClient: FusedLocationProviderClient =
                LocationServices.getFusedLocationProviderClient(it)
            fusedLocationClient.lastLocation
                .addOnSuccessListener { location: Location? ->
                    location?.let {
                        presenter.onGotLastLocation(location, isFromFab)
                    }
                }
        }

    }

    override fun animateToLocation(latLng: LatLng) {
        googleMap.animateCamera(
            CameraUpdateFactory.newLatLngZoom(
                latLng,
                ZOOM_MODE
            )
        )
    }

    override fun moveToLocation(latLng: LatLng) {
        googleMap.moveCamera(
            CameraUpdateFactory.newLatLngZoom(
                latLng,
                ZOOM_MODE
            )
        )
    }

    override fun setMarkerClickListener() {
        googleMap.setOnInfoWindowClickListener { marker ->
            presenter.onMarkerClicked(marker.tag.toString())
        }
    }

    override fun setDarkTheme() {
        googleMap.setMapStyle(
            MapStyleOptions.loadRawResourceStyle(
                context, R.raw.style_json
            )
        )
    }

    override fun addMarkers(markers: List<UiMarkerModel>) {
        markers.forEach {
            googleMap.addMarker(MarkerOptions().position(it.latLng).title(it.title)).tag =
                it.id
        }


    }


    companion object {

        const val TAG = "com.retar.go4lunch.ui.map.mapfragment"

        const val ZOOM_MODE = 15f


        @JvmStatic
        fun newInstance() = MapFragment()
    }

    override fun onDestroy() {
        presenter.onDestroy()
        super.onDestroy()
    }

}

