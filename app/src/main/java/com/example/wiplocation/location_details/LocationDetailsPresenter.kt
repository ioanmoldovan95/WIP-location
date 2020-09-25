package com.example.wiplocation.location_details

import com.example.wiplocation.base.BasePresenter
import com.example.wiplocation.model.WipLocation
import com.example.wiplocation.splash.LocationsDbCallback

class LocationDetailsPresenter(private val locationDetailsView: LocationDetailsView) :
    BasePresenter(locationDetailsView), LocationsDbCallback {

    fun getLocationByLabel(label: String) {
        realmDbService.getLocationByLabel(label, this)
    }

    override fun onGetLocationSuccess(result: WipLocation?) {
        result?.let {
            locationDetailsView.onLocationFetchedSuccess(it)
        }
    }

    fun saveLocation(location: WipLocation) {
        realmDbService.updateLocation(wipLocation = location)
    }
}