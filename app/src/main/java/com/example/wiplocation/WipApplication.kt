package com.example.wiplocation

import android.app.Application
import com.example.wiplocation.service.RealmDbService
import com.example.wiplocation.service.WipLocationService
import io.realm.Realm
import io.realm.RealmConfiguration
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class WipApplication : Application() {

    lateinit var wipLocationService: WipLocationService
    lateinit var realmDbService: RealmDbService

    override fun onCreate() {
        super.onCreate()

        val client = OkHttpClient.Builder().build()

        val retrofit = Retrofit.Builder()
            .baseUrl("http://demo1042273.mockable.io")
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        wipLocationService = retrofit.create(WipLocationService::class.java)

        Realm.init(applicationContext)
        val realmConfiguration = RealmConfiguration.Builder()
            .name("wip-app.db")
            .build()

        realmDbService = RealmDbService(Realm.getInstance(realmConfiguration))
    }
}