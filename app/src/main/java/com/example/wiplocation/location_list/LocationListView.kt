package com.example.wiplocation.location_list

import com.example.wiplocation.base.BaseView
import com.example.wiplocation.model.Location
import io.realm.RealmResults

interface LocationListView : BaseView{
    fun onLocationsReady(results: RealmResults<Location>?)

}