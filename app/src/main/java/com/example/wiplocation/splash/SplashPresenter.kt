package com.example.wiplocation.splash

import android.content.Context
import android.net.ConnectivityManager
import com.example.wiplocation.R
import com.example.wiplocation.model.LocationListResponse
import com.example.wiplocation.service.WipLocationService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SplashPresenter(val splashView: SplashView) {
    private val wipLocationService: WipLocationService =
        splashView.getWipApplication().wipLocationService

    fun getLocationsList() {
        if(!hasNetworkConnectivity()) {
            splashView.onNoNetworkConectivity()
        } else {
            wipLocationService.getLocationsList().enqueue(object : Callback<LocationListResponse> {
                override fun onResponse(
                    call: Call<LocationListResponse>?,
                    response: Response<LocationListResponse>?
                ) {
                    response?.body()?.let { locationListResponse ->
                        splashView.onGetLocationsListSuccess(locationListResponse.locations)
                    }

                }

                override fun onFailure(call: Call<LocationListResponse>?, t: Throwable?) {
                    splashView.onGetLocationsListFailure(
                        t?.localizedMessage
                            ?: splashView.getWipApplication().getString(R.string.unknown_error)
                    )
                }
            })
        }
    }

    fun hasNetworkConnectivity(): Boolean {
        val connectivityManager = splashView.getWipApplication().applicationContext.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetwork = connectivityManager.activeNetworkInfo
        return activeNetwork?.isConnectedOrConnecting ?: false
    }
}
