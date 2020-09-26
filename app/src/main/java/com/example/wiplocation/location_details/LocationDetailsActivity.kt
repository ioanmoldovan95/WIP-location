package com.example.wiplocation.location_details

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.ImageView
import android.widget.TextView
import androidx.core.app.NavUtils
import com.bumptech.glide.Glide
import com.example.wiplocation.R
import com.example.wiplocation.base.BaseActivity
import com.example.wiplocation.base.LocationDetailsDialog
import com.example.wiplocation.base.LocationDialogCallback
import com.example.wiplocation.location_list.LocationListActivity
import com.example.wiplocation.model.WipLocation

class LocationDetailsActivity : BaseActivity(), LocationDetailsView, LocationDialogCallback {
    lateinit var presenter: LocationDetailsPresenter
    lateinit var labelTextView: TextView
    lateinit var addressTextView: TextView
    lateinit var coordinatesTextView: TextView
    lateinit var locationImageView: ImageView

    private lateinit var currentLocationDetails: WipLocation

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_location_details)
        setSupportActionBar(findViewById(R.id.toolbar))
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        labelTextView = findViewById(R.id.label_text_view)
        addressTextView = findViewById(R.id.address_text_view)
        coordinatesTextView = findViewById(R.id.extra_details_text_view)
        locationImageView = findViewById(R.id.location_image)

        presenter = LocationDetailsPresenter(this)
        val locationLabel = intent.getStringExtra(LocationListActivity.LOCATION_LABEL_EXTRA)
        presenter.getLocationByLabel(locationLabel)
    }

    override fun onLocationFetchedSuccess(location: WipLocation) {
        currentLocationDetails = location
        labelTextView.text = location.label
        addressTextView.text = getString(R.string.address, location.address)
        coordinatesTextView.text = getString(R.string.coordinates, location.lat, location.lng)
        Glide.with(this).load(location.image).placeholder(R.drawable.ic_baseline_error_24)
            .into(locationImageView)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                NavUtils.navigateUpFromSameTask(this)
                true
            }
            R.id.edit_location -> {
                editCurrentLocation()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.details_activity_menu, menu)
        return true
    }

    private fun editCurrentLocation() {
        val dialog = LocationDetailsDialog()
        val arguments = Bundle()
        with(arguments) {
            putBoolean(IS_EDIT_MODE, true)
            putString(LABEL, currentLocationDetails.label)
            putString(ADDRESS, currentLocationDetails.address)
            putString(IMAGE_URL, currentLocationDetails.image)
            putString(LATITUDE, currentLocationDetails.lat.toString())
            putString(LONGITUDE, currentLocationDetails.lng.toString())
        }
        dialog.arguments = arguments
        dialog.setLocationDialogCallback(this)
        dialog.show(supportFragmentManager, "dialog")
    }

    override fun onPositiveButtonClick(location: WipLocation) {
        presenter.saveLocation(location)
    }

    companion object {
        const val IS_EDIT_MODE = "is_edit_mode"
        const val LABEL = "label"
        const val ADDRESS = "address"
        const val IMAGE_URL = "image_url"
        const val LATITUDE = "latitude"
        const val LONGITUDE = "longitude"
    }
}
