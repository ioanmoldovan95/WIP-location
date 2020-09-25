package com.example.wiplocation.location_details

import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.example.wiplocation.R
import com.example.wiplocation.base.BaseActivity
import com.example.wiplocation.location_list.LocationListActivity
import com.example.wiplocation.model.WipLocation

class LocationDetailsActivity : BaseActivity(), LocationDetailsView {
    lateinit var presenter: LocationDetailsPresenter
    lateinit var labelTextView: TextView
    lateinit var addressTextView: TextView
    lateinit var coordinatesTextView: TextView
    lateinit var locationImageView: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_location_details)
        labelTextView = findViewById(R.id.label_text_view)
        addressTextView = findViewById(R.id.address_text_view)
        coordinatesTextView = findViewById(R.id.coordinates_text_view)
        locationImageView = findViewById(R.id.location_image)

        presenter = LocationDetailsPresenter(this)
        val locationLabel = intent.getStringExtra(LocationListActivity.LOCATION_LABEL_EXTRA)
        presenter.getLocationByLabel(locationLabel)
    }

    override fun onLocationFetchedSuccess(location: WipLocation) {
        labelTextView.text = location.label
        addressTextView.text = getString(R.string.address, location.address)
        coordinatesTextView.text = getString(R.string.coordinates, location.lat, location.lng)
        Glide.with(this).load(location.image).placeholder(R.drawable.ic_baseline_error_24).into(locationImageView)
    }
}
