package com.retar.go4lunch.ui.map

import android.location.Location
import com.retar.go4lunch.repository.restaurant.RestaurantsRepository
import com.retar.go4lunch.repository.restaurant.model.RestaurantEntity
import com.retar.go4lunch.ui.map.model.UiMarkerModel
import com.retar.go4lunch.utils.getApiString
import com.retar.go4lunch.utils.getLatLng
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class MapViewPresenterImpl @Inject constructor(
    private val view: MapView,
    private val repository: RestaurantsRepository
) : MapViewPresenter {

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

    private fun loadNearbyRestaurants(locationString: String, distance: String = "500") {

        disposable =
            repository.getRestaurants(locationString, distance)
                .subscribeOn(Schedulers.io())
                .map {
                    mapRestaurantResponseToUi(it)
                }
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeBy(
                    onSuccess = {
                        view.addMarkers(it)
                    },
                    onError = {
                        //todo handle error on maps response
                    }
                )
    }

    private fun mapRestaurantResponseToUi(restaurantEntity: List<RestaurantEntity>): List<UiMarkerModel> {
        val markersList = mutableListOf<UiMarkerModel>()
        restaurantEntity.forEach {
            markersList.add(UiMarkerModel(it.latLng, it.name, it.id))
        }
        return markersList
    }

    override fun onDestroy() {
        disposable?.dispose()
    }

}