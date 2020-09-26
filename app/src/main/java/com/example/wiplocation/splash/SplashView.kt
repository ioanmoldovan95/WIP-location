package com.example.wiplocation.splash

import com.example.wiplocation.base.BaseView

interface SplashView : BaseView {
    fun goToLocationsListActivity(hasInternetConnection: Boolean = true)
    fun onNoNetworkConnectivity();
}