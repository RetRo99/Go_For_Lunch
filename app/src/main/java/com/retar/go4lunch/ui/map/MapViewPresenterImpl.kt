package com.retar.go4lunch.ui.map

import android.location.Location
import com.retar.go4lunch.base.model.RestaurantEntity
import com.retar.go4lunch.manager.contentdata.ContentDataManager
import com.retar.go4lunch.manager.location.LocationManager
import com.retar.go4lunch.repository.restaurant.RestaurantsRepository
import com.retar.go4lunch.ui.MainViewPresenter
import com.retar.go4lunch.ui.map.model.UiMarkerModel
import com.retar.go4lunch.ui.restaurantdetail.model.UiRestaurantDetailItem
import com.retar.go4lunch.utils.getLatLng
import io.reactivex.Observable
import io.reactivex.disposables.Disposable
import io.reactivex.rxkotlin.subscribeBy
import javax.inject.Inject

class MapViewPresenterImpl @Inject constructor(
    private val view: MapView,
    private val dataManager: ContentDataManager,
    private val parentPresenter: MainViewPresenter,
    private val locationManager: LocationManager

) : MapViewPresenter {

    private var disposable: Disposable? = null

    override fun onActivityCreated() {
        locationManager.makeLog()
        view.getMapAsync()
    }

    override fun onMapReady() {
        observerData()
        view.setDarkTheme()

    }

    override fun onGotLastLocation(location: Location, isFromFab: Boolean) {

        if (isFirstRun || isFromFab) {
            view.animateToLocation(location.getLatLng())
            isFirstRun = false
        } else {
            view.moveToLocation(location.getLatLng())
        }
        loadNearbyRestaurants(location, isFromFab)
    }

    override fun onFabClick() {
        view.getLastLocation(true)
    }

    private fun observerData() {
        disposable = dataManager.restaurants
            .flatMap {
                Observable.fromIterable(it)
            }
            .map {
                mapRestaurantResponseToUi(it)
            }

            .subscribeBy(
                onNext = {
                    view.addMarker(it)
                    view.setMarkerClickListener()
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


        dataManager
            .loadNearbyRestaurants(location, distance, isFromFab)

    }

    private fun mapRestaurantResponseToUi(restaurantEntity: RestaurantEntity): UiMarkerModel {
        return UiMarkerModel(restaurantEntity.latLng, restaurantEntity.name, restaurantEntity.id, restaurantEntity.icon)
    }

    override fun onMarkerClicked(id: String, title: String) {
        parentPresenter.fromMapToRestaurantDetail(id, title)

    }

    override fun onDestroy() {
        disposable?.dispose()
    }

    companion object {
        private var isFirstRun = true

        private const val DEFAULT_DISTANCE = "1500"
    }
}