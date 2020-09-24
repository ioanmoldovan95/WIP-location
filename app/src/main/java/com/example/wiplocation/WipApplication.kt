package com.example.wiplocation

import android.app.Application
import com.example.wiplocation.service.WipLocationService
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class WipApplication : Application() {

    lateinit var wipLocationService: WipLocationService

    override fun onCreate() {
        super.onCreate()

        val client = OkHttpClient.Builder().build()

        val retrofit = Retrofit.Builder()
            .baseUrl("http://demo1042273.mockable.io")
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        wipLocationService = retrofit.create(WipLocationService::class.java)
    }
}