package com.example.wiplocation.model

import com.example.wiplocation.WipApplication
import io.realm.RealmObject
import io.realm.annotations.PrimaryKey

open class WipLocation() : RealmObject() {
    @PrimaryKey
    var label: String = WipApplication.EMPTY_STRING
    var address: String = WipApplication.EMPTY_STRING
    var image: String = WipApplication.EMPTY_STRING
    var lat: Double = 0.0
    var lng: Double = 0.0
    var distance: String = WipApplication.EMPTY_STRING

    constructor(label: String, address: String, image: String, lat: Double, lng: Double) : this() {
        this.label = label
        this.address = address
        this.image = image
        this.lat = lat
        this.lng = lng
    }
}