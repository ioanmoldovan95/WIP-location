package com.example.wiplocation.location_list

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.wiplocation.R
import com.example.wiplocation.model.WipLocation
import io.realm.RealmRecyclerViewAdapter
import io.realm.RealmResults

class LocationsAdapter(private var wipLocationResults: RealmResults<WipLocation>) :
    RealmRecyclerViewAdapter<WipLocation, LocationViewHolder>(wipLocationResults, true, true) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LocationViewHolder {
        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.location_item, parent, false)
        return LocationViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: LocationViewHolder, position: Int) {
        holder.bind(wipLocationResults[position])
    }

    fun setLocations(wipLocationResults: RealmResults<WipLocation>) {
        this.wipLocationResults = wipLocationResults
        notifyDataSetChanged()
    }
}

class LocationViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    private val addressTextView = itemView.findViewById<TextView>(R.id.address)
    private val distanceTextView = itemView.findViewById<TextView>(R.id.distance)
    private val locationImageView = itemView.findViewById<ImageView>(R.id.location_image)

    fun bind(wipLocation: WipLocation?) {
        addressTextView.text = "Address " + wipLocation?.address ?: ""
        distanceTextView.text = "Distance " + wipLocation?.distance ?: ""
        //TODO show image
    }
}
