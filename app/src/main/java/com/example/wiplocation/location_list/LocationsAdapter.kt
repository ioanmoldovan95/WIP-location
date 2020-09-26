package com.example.wiplocation.location_list

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.wiplocation.R
import com.example.wiplocation.WipApplication
import com.example.wiplocation.model.WipLocation
import io.realm.RealmRecyclerViewAdapter
import io.realm.RealmResults

class LocationsAdapter(
    private var wipLocationResults: RealmResults<WipLocation>,
    private val callback: LocationsListClickListener,
    private var recyclerViewOrientation: RecyclerViewOrientation = RecyclerViewOrientation.VERTICAL
) :
    RealmRecyclerViewAdapter<WipLocation, LocationVerticalViewHolder>(wipLocationResults, true, true) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LocationVerticalViewHolder {
        val itemView =
            LayoutInflater.from(parent.context).inflate(
                if (recyclerViewOrientation == RecyclerViewOrientation.VERTICAL) {
                    R.layout.location_item_vertical
                } else {
                    R.layout.location_item_horizontal
                }, parent, false
            )
        return LocationVerticalViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: LocationVerticalViewHolder, position: Int) {
        holder.bind(wipLocationResults[position])
        holder.itemView.setOnClickListener {
            callback.onLocationClicked(wipLocationResults[position]?.label ?: WipApplication.EMPTY_STRING)
        }
    }

    fun setLocations(wipLocationResults: RealmResults<WipLocation>) {
        this.wipLocationResults = wipLocationResults
        notifyDataSetChanged()
    }

    fun setRecyclerViewOrientation(recyclerViewOrientation: RecyclerViewOrientation) {
        this.recyclerViewOrientation = recyclerViewOrientation
    }
}

class LocationVerticalViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    private val addressTextView = itemView.findViewById<TextView>(R.id.address)
    private val distanceTextView = itemView.findViewById<TextView>(R.id.distance)
    private val locationImageView = itemView.findViewById<ImageView>(R.id.location_image)

    fun bind(wipLocation: WipLocation?) {
        addressTextView.text = itemView.resources.getString(R.string.address, wipLocation?.address ?: WipApplication.EMPTY_STRING)
        distanceTextView.text = itemView.resources.getString(R.string.distance, wipLocation?.distance ?: WipApplication.EMPTY_STRING)
        Glide.with(itemView).load(wipLocation?.image).error(R.drawable.ic_baseline_error_24).into(locationImageView)
    }
}
