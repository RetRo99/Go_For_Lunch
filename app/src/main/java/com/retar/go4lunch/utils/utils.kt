package com.retar.go4lunch.utils

import android.content.Context
import android.location.Location
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.ImageView
import androidx.annotation.LayoutRes
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.google.android.gms.maps.model.LatLng
import com.retar.go4lunch.R
import com.retar.go4lunch.base.Constants.GOOGLE_KEY


fun Location.getApiString(): String {
    return "${this.latitude},${this.longitude}"
}

fun Location.getLatLng(): LatLng {
    return LatLng(this.latitude, this.longitude)
}

fun ViewGroup.inflate(@LayoutRes layoutRes: Int, attachToRoot: Boolean = false): View {
    return LayoutInflater.from(context).inflate(layoutRes, this, attachToRoot)
}

fun ImageView.loadRestaurantPhoto(photoReference: String?) {
    Glide.with(this)
        .load("https://maps.googleapis.com/maps/api/place/photo?maxwidth=400&photoreference=$photoReference&key=$GOOGLE_KEY")
        .placeholder(R.drawable.ic_restaurant)
        .error(R.drawable.ic_restaurant)
        .into(this)


}

fun ImageView.loadRoundPhoto(url: String?) {
    Glide.with(this)
        .load(url)
        .apply(RequestOptions.circleCropTransform())
        .into(this)


}

fun View.showKeyboard() {
    this.requestFocus()
    val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager

    imm.showSoftInput(this, InputMethodManager.SHOW_IMPLICIT)

}


fun View.hideKeyboard() {
    val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager

    imm.hideSoftInputFromWindow(windowToken, 0)

}
