package com.retar.go4lunch.ui.map

import android.util.Log
import com.retar.go4lunch.R
import com.retar.go4lunch.base.model.RestaurantEntity
import com.retar.go4lunch.manager.contentdata.ContentDataManager
import com.retar.go4lunch.manager.location.LocationManager
import com.retar.go4lunch.ui.MainViewPresenter
import com.retar.go4lunch.ui.map.model.UiMarkerModel
import com.retar.go4lunch.utils.getLatLng
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.subjects.BehaviorSubject
import java.util.*
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class MapViewPresenterImpl @Inject constructor(
    private val view: MapView,
    private val dataManager: ContentDataManager,
    private val parentPresenter: MainViewPresenter,
    private val locationManager: LocationManager

) : MapViewPresenter {

    private val searchText: BehaviorSubject<String> = BehaviorSubject.create()

    private val compositeDisposable = CompositeDisposable()


    override fun onActivityCreated() {
        compositeDisposable.add(searchText
            .debounce(750, TimeUnit.MILLISECONDS)
            .distinctUntilChanged()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy(
                onNext = {
                    view.deleteAllMarkers()
                    dataManager.searchAutoComplete(it, uniqueId)
                        .observeOn(AndroidSchedulers.mainThread())
                        .flattenAsObservable { it }
                        .map { restaurantEntity ->
                            mapRestaurantResponseToUi(restaurantEntity)
                        }
                        .subscribeBy(
                            onNext = { marker ->
                                view.addMarker(marker)
                            }
                        )
                }
            )
        )
        view.getMapAsync()
    }

    override fun onMapReady() {
        observerData()
        view.setMarkerClickListener()
        view.setDarkTheme()
    }

    override fun onFabClick() {
        locationManager.updateLocation()
    }

    private fun observerData() {

        compositeDisposable.add(dataManager.restaurants
            .flatMap {
                Observable.fromIterable(it)
            }
            .map {
                mapRestaurantResponseToUi(it)
            }
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy(
                onNext = {
                    view.addMarker(it)
                },
                onError = {
                    view.showToast(R.string.error_no_data)
                }
            ))

        compositeDisposable.add(locationManager.location
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy(
                onNext = {
                    view.moveToLocation(it.getLatLng())
                }
            ))
    }


    private fun mapRestaurantResponseToUi(restaurantEntity: RestaurantEntity): UiMarkerModel {
        return UiMarkerModel(
            restaurantEntity.latLng,
            restaurantEntity.name,
            restaurantEntity.id,
            restaurantEntity.icon
        )
    }

    override fun onMarkerClicked(id: String, title: String) {
        uniqueId = UUID.randomUUID().toString()
        parentPresenter.fromMapToRestaurantDetail(id, title)
    }

    override fun onSearchChanged(text: CharSequence?) {
        searchText.onNext(text.toString())
    }

    override fun onDestroy() {
        compositeDisposable.dispose()
    }

    companion object{
        private var uniqueId = UUID.randomUUID().toString()
    }

}