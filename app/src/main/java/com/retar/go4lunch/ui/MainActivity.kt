package com.retar.go4lunch.ui

import com.retar.go4lunch.base.LocationPermissionActivity
import com.retar.go4lunch.ui.holderfragment.HolderFragmentDirections

class MainActivity : LocationPermissionActivity(), MainView {


    override fun fromHolderToResturantDetail(id: String) {
        getNavController().navigate(HolderFragmentDirections.actionToRestaurantDetailFragment(id))
    }

}