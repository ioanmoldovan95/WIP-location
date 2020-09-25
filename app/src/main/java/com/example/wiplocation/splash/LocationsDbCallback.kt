package com.example.wiplocation.splash

import com.example.wiplocation.model.WipLocation
import io.realm.RealmResults

interface LocationsDbCallback {
    fun onPersistSuccess() {}
    fun onPersistFailed(error: String?) {}
    fun onReadLocationsSuccess(results: RealmResults<WipLocation>) {}
    fun onGetLocationSuccess(result: WipLocation?) {}
}