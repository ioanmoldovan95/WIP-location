package com.example.wiplocation.location_list

import com.example.wiplocation.base.BasePresenter
import com.example.wiplocation.model.Location
import com.example.wiplocation.splash.LocationsDbCallback
import io.realm.RealmResults

class LocationsListPresenter(private val locationListView: LocationListView) : BasePresenter(locationListView), LocationsDbCallback {

    fun readLocations() {
        realmDbService.readLocations(this)
    }

    override fun onReadLocationsSuccess(results: RealmResults<Location>?) {
        locationListView.onLocationsReady(results)
    }
}