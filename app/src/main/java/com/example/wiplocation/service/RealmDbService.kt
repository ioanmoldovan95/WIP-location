package com.example.wiplocation.service

import com.example.wiplocation.model.Location
import com.example.wiplocation.splash.LocationsDbCallback
import io.realm.Realm
import io.realm.RealmResults

class RealmDbService(private val realm: Realm) {

    fun persistLocations(locationsList: ArrayList<Location>, callback: LocationsDbCallback) {
        realm.executeTransactionAsync({
            for (location in locationsList) {
                it.insertOrUpdate(location)
            }
        },
            { callback.onPersistSuccess() },
            { error -> callback.onPersistFailed(error.localizedMessage) })
    }

    fun readLocations(callback: LocationsDbCallback) {
        realm.executeTransaction {
            val results = it.where(Location::class.java).findAll()
            callback.onReadLocationsSuccess(results)
        }
    }
}