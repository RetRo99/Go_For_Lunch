package com.retar.go4lunch.manager.location

import android.app.Activity
import android.util.Log
import javax.inject.Inject

class LocationManager @Inject constructor(
    private val activity: Activity
){
    fun makeLog(){
        Log.d("čič", "čič")
    }

}