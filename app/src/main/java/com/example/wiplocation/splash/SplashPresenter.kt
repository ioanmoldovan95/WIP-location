package com.example.wiplocation.splash

import android.content.Context
import android.net.ConnectivityManager
import com.example.wiplocation.R
import com.example.wiplocation.base.BasePresenter
import com.example.wiplocation.model.LocationListResponse
import com.example.wiplocation.webservice.WipLocationService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SplashPresenter(
    val splashView: SplashView,
): BasePresenter(splashView), LocationsDbCallback {
    private val wipLocationService: WipLocationService =
        splashView.getWipApplication().wipLocationService

    fun getLocationsList() {
        if (!hasNetworkConnectivity()) {
            if(realmDbService.hasData()){
                splashView.goToLocationsListActivity(false)
            } else {
                splashView.onNoNetworkConnectivity()
            }
        } else {
            wipLocationService.getLocationsList().enqueue(object : Callback<LocationListResponse> {
                override fun onResponse(
                    call: Call<LocationListResponse>?,
                    response: Response<LocationListResponse>?
                ) {
                    response?.body()?.let { locationListResponse ->
                        realmDbService.persistLocations(locationsList = locationListResponse.locations, this@SplashPresenter)
                    }
                }

                override fun onFailure(call: Call<LocationListResponse>?, t: Throwable?) {
                    splashView.showErrorMessage(
                        t?.localizedMessage
                            ?: splashView.getWipApplication().getString(R.string.unknown_error)
                    )
                }
            })
        }
    }

    private fun hasNetworkConnectivity(): Boolean {
        val connectivityManager =
            splashView.getWipApplication().applicationContext.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetwork = connectivityManager.activeNetworkInfo
        return activeNetwork?.isConnectedOrConnecting ?: false
    }

    override fun onPersistSuccess() {
        splashView.goToLocationsListActivity()
    }

    override fun onPersistFailed(error: String?) {
        splashView.showErrorMessage(error ?: splashView.getWipApplication().getString(R.string.unknown_error))
    }
}
