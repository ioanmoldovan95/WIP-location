package com.example.wiplocation.splash

import com.example.wiplocation.WipApplication

interface SplashView {
    fun goToLocationsListActivity()
    fun showErrorMessage(errorMessage: String)
    fun onNoNetworkConnectivity();
    fun getWipApplication(): WipApplication
}