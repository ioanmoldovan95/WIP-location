package com.example.wiplocation.location_list

import android.view.View

interface LocationsListClickListener {
    fun onLocationClicked(label: String, view: View)
}
