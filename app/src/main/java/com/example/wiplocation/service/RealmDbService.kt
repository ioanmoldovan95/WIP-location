package com.example.wiplocation.service

import com.example.wiplocation.model.Location
import com.example.wiplocation.splash.LocationsDbCallback
import io.realm.Realm

class RealmDbService(private val realm: Realm) {

    fun persistLocations(locationsList: ArrayList<Location>, callback: LocationsDbCallback) {
        realm.executeTransactionAsync({
            for (location in locationsList) {
                it.insertOrUpdate(location)
            }
        },
            { callback.onPersistSuccess() },
            { error -> callback.onPersistFailed(error.localizedMessage) })
//        realm.executeTransaction {
//            for (location in locationsList) {
//                it.insertOrUpdate(location)
//            }
//        }
//        callback.onPersistSuccess()
    }

    fun readLocations(callback: LocationsDbCallback) {
        val results = realm.where(Location::class.java).findAll()
        callback.onReadLocationsSuccess(results)
    }

    fun hasData(): Boolean {
        return realm.where(Location::class.java).findAll().size > 0
    }
}