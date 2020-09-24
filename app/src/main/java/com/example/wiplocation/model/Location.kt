package com.example.wiplocation.model

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey

open class Location() : RealmObject() {
    @PrimaryKey
    var label: String = ""
    var address: String = ""
    var image: String = ""
    var lat: Double = 0.0
    var lng: Double = 0.0

    constructor(label: String, address: String, image: String, lat: Double, lng: Double) : this() {
        this.label = label
        this.address = address
        this.image = image
        this.lat = lat
        this.lng = lng
    }
}