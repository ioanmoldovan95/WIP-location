package com.example.wiplocation.location_list

import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.wiplocation.R
import com.example.wiplocation.base.BaseActivity
import com.example.wiplocation.model.Location
import io.realm.RealmResults

class LocationListActivity : BaseActivity(), LocationListView {
    lateinit var locationsRecyclerView: RecyclerView
    private lateinit var presenter : LocationsListPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_location_list)
        presenter = LocationsListPresenter(this)
        locationsRecyclerView = findViewById(R.id.location_recycler_view)
        presenter.readLocations()
    }

    override fun onLocationsReady(results: RealmResults<Location>?) {
        if(results != null) {
            locationsRecyclerView.layoutManager = LinearLayoutManager(applicationContext)
            locationsRecyclerView.adapter =
                LocationsAdapter(results)
        }
    }
}