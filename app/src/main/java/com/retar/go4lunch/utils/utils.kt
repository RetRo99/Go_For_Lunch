package com.retar.go4lunch.utils

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.location.Location
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.annotation.LayoutRes
import com.bumptech.glide.Glide
import com.google.android.gms.maps.model.LatLng
import com.retar.go4lunch.R
import com.retar.go4lunch.ui.list.ListFragment

fun Location.getApiString():String{
    return "${this.latitude},${this.longitude}"
}

fun Location.getLatLng():LatLng{
    return LatLng(this.latitude, this.longitude)
}

fun ViewGroup.inflate(@LayoutRes layoutRes: Int, attachToRoot: Boolean = false): View {
    return LayoutInflater.from(context).inflate(layoutRes, this, attachToRoot)
}

fun ImageView.loadPhotoFromUrl(photoReference: String?) {
        Glide.with(this)
            .load("https://maps.googleapis.com/maps/api/place/photo?maxwidth=400&photoreference=$photoReference&key=AIzaSyB6JuG-GiQgQG2RixaTKyhqBlhT9Uklr6Y\n")
            .placeholder(R.drawable.ic_restaurant)
            .error(R.drawable.ic_restaurant)
            .into(this)
    Log.d(ListFragment.TAG, "loadinphoto")


}