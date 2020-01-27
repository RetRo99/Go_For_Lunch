package com.retar.go4lunch.ui.map

import android.location.Location
import com.retar.go4lunch.repository.restaurant.RestaurantsRepository
import com.retar.go4lunch.repository.restaurant.model.RestaurantEntity
import com.retar.go4lunch.ui.MainViewPresenter
import com.retar.go4lunch.ui.map.model.UiMarkerModel
import com.retar.go4lunch.utils.getLatLng
import io.reactivex.disposables.Disposable
import io.reactivex.rxkotlin.subscribeBy
import javax.inject.Inject

class MapViewPresenterImpl @Inject constructor(
    private val view: MapView,
    private val repository: RestaurantsRepository,
    private val parentPresenter: MainViewPresenter

) : MapViewPresenter {

    private var disposable: Disposable? = null

    override fun onActivityCreated() {
        view.getMapAsync()
    }

    override fun onMapReady() {
        observerData()
        view.getLastLocation(isFromFab = false)
    }

    override fun onGotLastLocation(location: Location, isFromFab: Boolean) {
        view.zoomToLocation(location.getLatLng())
        loadNearbyRestaurants(location, isFromFab)
    }

    override fun onFabClick() {
        view.getLastLocation(true)
    }

    private fun observerData() {
        disposable = repository.list
            .map {
                mapRestaurantResponseToUi(it)
            }
            .subscribeBy(
                onNext = {
                    view.addMarkers(it)
                },
                onError = {
                    ///todo handle error
                }
            )
    }

    private fun loadNearbyRestaurants(
        location: Location,
        isFromFab: Boolean,
        distance: String = DEFAULT_DISTANCE
    ) {


        repository
            .getRestaurants(location, distance, isFromFab)

    }

    private fun mapRestaurantResponseToUi(restaurantEntity: List<RestaurantEntity>): List<UiMarkerModel> {
        val markersList = mutableListOf<UiMarkerModel>()
        restaurantEntity.forEach {
            markersList.add(UiMarkerModel(it.latLng, it.name, it.id))
        }
        return markersList
    }

    override fun onMarkerClicked(id: String) {
        parentPresenter.toRestaurantDetail()

    }

    override fun onDestroy() {
        disposable?.dispose()
    }

    companion object {

        private const val DEFAULT_DISTANCE = "1500"
    }
}