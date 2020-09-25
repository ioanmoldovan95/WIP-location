package com.example.wiplocation.location_list

import android.annotation.SuppressLint
import android.content.Context
import android.location.Location
import android.location.LocationManager
import androidx.appcompat.app.AppCompatActivity
import com.example.wiplocation.base.BasePresenter
import com.example.wiplocation.model.WipLocation
import com.example.wiplocation.splash.LocationsDbCallback
import com.google.android.gms.location.LocationServices
import com.google.android.gms.tasks.OnFailureListener
import com.google.android.gms.tasks.OnSuccessListener
import io.realm.RealmResults

class LocationsListPresenter(private val locationListView: LocationListView) : BasePresenter(locationListView), LocationsDbCallback {

    fun readLocations() {
        realmDbService.readLocations(this)
    }

    override fun onReadLocationsSuccess(results: RealmResults<WipLocation>) {
        locationListView.updateLocations(results, shouldInit = true)
    }

    @SuppressLint("MissingPermission")
    fun computeLocations(data: RealmResults<WipLocation>) {
        val locationProvider = LocationServices.getFusedLocationProviderClient(locationListView as AppCompatActivity)
        locationProvider.lastLocation.addOnSuccessListener(OnSuccessListener {
            for (wipLocation in data) {
                val location = Location("")
                location.latitude = wipLocation.lat
                location.longitude = wipLocation.lng
                val distance = location.distanceTo(it)
                realmDbService.updateLocation(wipLocation, getDistanceString(distance))
            }
            locationListView.updateLocations(data, false)
        }).addOnFailureListener(OnFailureListener {
            locationListView.showLocationFailedErrorMessage()
        })
    }

    private fun getDistanceString(distance: Float) : String {
        return if (distance < 1000) {
            "$distance meters"
        } else {
            val normDistance = distance / 1000
            "$normDistance km"
        }
    }
}
