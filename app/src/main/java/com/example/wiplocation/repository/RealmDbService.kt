package com.example.wiplocation.repository

import com.example.wiplocation.model.WipLocation
import com.example.wiplocation.splash.LocationsDbCallback
import io.realm.Realm

class RealmDbService(private val realm: Realm) {

    fun persistLocations(locationsList: ArrayList<WipLocation>, callback: LocationsDbCallback) {
        realm.executeTransactionAsync({
            for (location in locationsList) {
                it.insertOrUpdate(location)
            }
        },
            { callback.onPersistSuccess() },
            { error -> callback.onPersistFailed(error.localizedMessage) })
    }

    fun readLocations(callback: LocationsDbCallback) {
        val results = realm.where(WipLocation::class.java).findAll()
        callback.onReadLocationsSuccess(results)
    }

    fun updateLocation(wipLocation: WipLocation, distance: String) {
        realm.executeTransaction {
            wipLocation.distance = distance
            it.insertOrUpdate(wipLocation)
        }
    }

    fun updateLocation(wipLocation: WipLocation) {
        realm.executeTransaction {
            it.insertOrUpdate(wipLocation)
        }
    }

    fun getLocationByLabel(label: String, callback:LocationsDbCallback) {
        val result = realm.where(WipLocation::class.java).equalTo("label", label).findFirst()
        callback.onGetLocationSuccess(result)
    }

    fun hasData(): Boolean {
        return realm.where(WipLocation::class.java).findAll().size > 0
    }
}