package com.retar.go4lunch.base

import androidx.navigation.NavController

interface ProvideNavController {

    fun getNavController(): NavController
}