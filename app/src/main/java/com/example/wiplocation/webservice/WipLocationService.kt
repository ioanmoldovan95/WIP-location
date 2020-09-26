package com.example.wiplocation.webservice

import com.example.wiplocation.model.LocationListResponse
import retrofit2.Call
import retrofit2.http.GET

interface WipLocationService {

    @GET("/mylocations")
    fun getLocationsList(): Call<LocationListResponse>
}
