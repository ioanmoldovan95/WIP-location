package com.example.wiplocation.splash

import com.example.wiplocation.WipApplication
import com.example.wiplocation.model.Location

interface SplashView {
    fun onGetLocationsListSuccess(locationsList: ArrayList<Location>)
    fun onGetLocationsListFailure(errorMessage: String)
    fun onNoNetworkConectivity();
    fun getWipApplication(): WipApplication
}