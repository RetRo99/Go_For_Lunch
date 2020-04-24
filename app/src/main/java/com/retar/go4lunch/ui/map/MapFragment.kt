package com.retar.go4lunch.ui.map


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import android.widget.Toast
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.*
import com.retar.go4lunch.R
import com.retar.go4lunch.base.BaseAutoCompleteFragment
import com.retar.go4lunch.ui.map.model.UiMarkerModel
import kotlinx.android.synthetic.main.fragment_map_view.*
import javax.inject.Inject

class MapFragment : BaseAutoCompleteFragment(), MapView,
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

        autoSearch.doOnTextChanged { text, _, _, _ ->
            presenter.onSearchChanged(text)
        }

        presenter.onActivityCreated()

    }

    override fun onMapReady(map: GoogleMap?) {
        map?.let {
            googleMap = it
            val locationButton = (mapFragment?.view?.findViewById<View>(Integer.parseInt("1"))?.parent as View).findViewById<View>(Integer.parseInt("2"))
            val rlp =  locationButton.layoutParams as RelativeLayout.LayoutParams
            rlp.addRule(RelativeLayout.ALIGN_PARENT_TOP, 0)
            rlp.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM, RelativeLayout.TRUE)
            rlp.setMargins(0, 0, 30, 30)
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

    override fun showToast(stringResource: Int) {
        Toast.makeText(context, stringResource, Toast.LENGTH_LONG).show()
    }

    override fun moveToLocation(latLng: LatLng) {
        googleMap.moveCamera(
            CameraUpdateFactory.newLatLngZoom(
                latLng,
                ZOOM_MODE
            )
        )
        googleMap.isMyLocationEnabled = true

    }

    override fun setMarkerClickListener() {
        googleMap.setOnInfoWindowClickListener { marker ->
            presenter.onMarkerClicked(marker.tag.toString(), marker.title)
        }
    }

    override fun setDarkTheme() {
        googleMap.setMapStyle(
            MapStyleOptions.loadRawResourceStyle(
                context, R.raw.style_json
            )
        )
    }

    override fun deleteAllMarkers() {
        googleMap.clear()
    }

    override fun addMarker(marker: UiMarkerModel) {
        val mapMarker = MarkerOptions().position(marker.latLng).title(marker.title).run {
            icon(BitmapDescriptorFactory.fromResource(marker.icon))
        }
        googleMap.addMarker(mapMarker).tag = marker.id
    }

    companion object {

        const val TAG = "com.retar.go4lunch.ui.map.mapfragment"

        const val ZOOM_MODE = 14.3f

        @JvmStatic
        fun newInstance() = MapFragment()
    }

    override fun onDestroy() {
        presenter.onDestroy()
        super.onDestroy()
    }

}

