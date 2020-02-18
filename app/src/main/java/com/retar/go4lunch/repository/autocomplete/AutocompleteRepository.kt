package com.retar.go4lunch.repository.autocomplete

import android.location.Location
import com.retar.go4lunch.base.model.RestaurantEntity
import io.reactivex.Single

interface AutocompleteRepository {

    fun getAutocompleteResult(
        searchParam: String,
        location: Location,
        radius: String,
        uniqueId:String
    ): Single<List<RestaurantEntity>>

}