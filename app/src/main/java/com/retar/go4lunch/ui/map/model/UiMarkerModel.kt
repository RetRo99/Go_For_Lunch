package com.retar.go4lunch.ui.map.model

import androidx.annotation.DrawableRes
import com.google.android.gms.maps.model.LatLng

data class UiMarkerModel(
    val latLng: LatLng,
    val title: String,
    val id: String,
    @DrawableRes val icon: Int
)