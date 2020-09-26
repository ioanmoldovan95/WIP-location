package com.example.wiplocation.location_list

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearSnapHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.wiplocation.R
import com.example.wiplocation.base.BaseActivity
import com.example.wiplocation.base.LocationDetailsDialog
import com.example.wiplocation.base.LocationDialogCallback
import com.example.wiplocation.location_details.LocationDetailsActivity
import com.example.wiplocation.model.WipLocation
import com.google.android.material.floatingactionbutton.FloatingActionButton
import io.realm.RealmResults

class LocationListActivity : BaseActivity(), LocationListView, LocationsListClickListener, LocationDialogCallback {
    lateinit var locationsRecyclerView: RecyclerView
    private lateinit var presenter: LocationsListPresenter
    private var recyclerViewOrientation = RecyclerViewOrientation.VERTICAL
    private val verticalLinearLayoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
    private val horizontalLinearLayoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
    private lateinit var recyclerViewAdapter: LocationsAdapter
    val snapHelper = LinearSnapHelper()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_location_list)
        setSupportActionBar(findViewById(R.id.toolbar))
        presenter = LocationsListPresenter(this)
        locationsRecyclerView = findViewById(R.id.location_recycler_view)
        locationsRecyclerView.layoutManager = verticalLinearLayoutManager
        val fab = findViewById<FloatingActionButton>(R.id.fab)
        fab.setOnClickListener {
            addNewLocation()
        }
        presenter.readLocations()
    }

    private fun addNewLocation() {
        val dialog = LocationDetailsDialog()
        dialog.setLocationDialogCallback(this)
        dialog.show(supportFragmentManager, "dialog")
    }

    private fun hasLocationPermissions(): Boolean {
        if (ContextCompat.checkSelfPermission(
                this.applicationContext,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(
                this.applicationContext,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(
                    Manifest.permission.ACCESS_COARSE_LOCATION,
                    Manifest.permission.ACCESS_FINE_LOCATION
                ),
                LOCATION_PERMISSION_REQUEST_CODE
            )
            return false
        }
        return true
    }

    override fun updateLocations(results: RealmResults<WipLocation>, shouldInit: Boolean) {
        if (shouldInit) {
            recyclerViewAdapter = LocationsAdapter(results, this, recyclerViewOrientation)
            locationsRecyclerView.adapter = recyclerViewAdapter
            if (hasLocationPermissions()) {
                presenter.computeLocations(results)
            }
        } else {
           recyclerViewAdapter.setLocations(results)
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == LOCATION_PERMISSION_REQUEST_CODE) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                presenter.computeLocations(recyclerViewAdapter.data as RealmResults<WipLocation>)
            } else {
                showLocationFailedErrorMessage()
            }
        }
    }

    override fun showLocationFailedErrorMessage() {
        showErrorMessage(getString(R.string.location_error_string))
    }

    override fun onLocationClicked(label: String) {
        val intent = Intent(this, LocationDetailsActivity::class.java)
        intent.putExtra(LOCATION_LABEL_EXTRA, label)
        startActivity(intent)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.list_activity_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.toggle_orientation -> {
                toggleOrientation()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun toggleOrientation() {
        if (recyclerViewOrientation == RecyclerViewOrientation.VERTICAL) {
            locationsRecyclerView.layoutManager = horizontalLinearLayoutManager
            recyclerViewOrientation = RecyclerViewOrientation.HORIZONTAL
            snapHelper.attachToRecyclerView(locationsRecyclerView)
        } else {
            locationsRecyclerView.layoutManager = verticalLinearLayoutManager
            recyclerViewOrientation = RecyclerViewOrientation.VERTICAL
            snapHelper.attachToRecyclerView(null)
        }
        recyclerViewAdapter.setRecyclerViewOrientation(recyclerViewOrientation)
        locationsRecyclerView.adapter = recyclerViewAdapter
    }

    override fun onPositiveButtonClick(location: WipLocation) {
        presenter.addNewLocation(location)
    }

    companion object {
        const val LOCATION_PERMISSION_REQUEST_CODE = 10001
        const val LOCATION_LABEL_EXTRA = "location_label_extra"
    }
}