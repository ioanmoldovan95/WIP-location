package com.example.wiplocation.location_list

import com.example.wiplocation.base.BaseView
import com.example.wiplocation.model.WipLocation
import io.realm.RealmResults

interface LocationListView : BaseView {
    fun updateLocations(results: RealmResults<WipLocation>, shouldInit: Boolean)
    fun showLocationFailedErrorMessage()
}