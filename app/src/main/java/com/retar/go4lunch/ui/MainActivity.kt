package com.retar.go4lunch.ui

import android.widget.Toast
import com.retar.go4lunch.base.LocationPermissionActivity

class MainActivity : LocationPermissionActivity(), MainView {


    override fun fromHolderToResturantDetail() {
        Toast.makeText(this, "hehe", Toast.LENGTH_LONG).show()
    }


}