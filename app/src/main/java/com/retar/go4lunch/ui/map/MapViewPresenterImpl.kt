package com.retar.go4lunch.ui.map

import android.annotation.SuppressLint
import android.location.Location
import android.util.Log
import com.retar.go4lunch.ApiClient
import com.retar.go4lunch.utils.getApiString
import com.retar.go4lunch.utils.getLatLng
import com.retar.go4lunch.ui.map.model.UiMarkerModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class MapViewPresenterImpl @Inject constructor(private val view: MapView) : MapViewPresenter {

    private var disposable: Disposable? = null

    override fun onActivityCreated() {
        view.getMapAsync()
    }

    override fun onMapReady() {
        view.getLastLocation()
    }

    override fun onGotLastLocation(location: Location) {
        view.zoomToLocation(location.getLatLng())

        loadNearbyRestaurants(location.getApiString())


    }

    override fun zoomToCurrentLocation() {
        view.getLastLocation()
    }

    @SuppressLint("CheckResult")
    fun loadNearbyRestaurants(locationString: String, distance: String = "500") {

        val observable =
            ApiClient.getGoogleMapsRestaurants.getNearbySquareRestaurants(locationString, distance)

        observable
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy(
                onSuccess = {
                    it.results.forEach {
                        val marker =
                            UiMarkerModel(it.geometry.location.getLatLng(), it.name, it.place_id)
                        view.addMarker(
                            marker
                        )
                    }
                },
                onError = { Log.d("čič", it.localizedMessage) }
            )
    }

    override fun onDetach() {
        //Todo
    }

}