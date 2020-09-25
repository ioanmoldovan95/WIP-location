package com.example.wiplocation.base

import android.app.Dialog
import android.os.Bundle
import android.text.InputType
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.fragment.app.DialogFragment
import com.example.wiplocation.R
import com.example.wiplocation.WipApplication
import com.example.wiplocation.location_details.LocationDetailsActivity
import com.example.wiplocation.model.WipLocation
import kotlin.math.ln

class LocationDetailsDialog : DialogFragment() {

    private lateinit var lngEditText: EditText
    private lateinit var latEditText: EditText
    private lateinit var imageEditText: EditText
    private lateinit var addressEditText: EditText
    private lateinit var labelEditText: EditText
    private var isEditMode = false

    private lateinit var callback: LocationDialogCallback

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.edit_location_dialog, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        isEditMode = arguments?.getBoolean(LocationDetailsActivity.IS_EDIT_MODE, false) ?: false
        val titleTextView = view.findViewById<TextView>(R.id.title)
        titleTextView.text = if (isEditMode) {
            view.resources.getString(R.string.edit_location_title)
        } else {
            view.resources.getString(R.string.add_location_title)
        }
        setupEditTexts(view)
        setupButtons(view)
    }

    private fun setupButtons(view: View) {
        val positiveButton = view.findViewById<Button>(R.id.positive_button)
        val negativeButton = view.findViewById<Button>(R.id.negative_button)
        negativeButton.setOnClickListener {
            this.dismiss()
        }
        positiveButton.setOnClickListener {
            val label = labelEditText.text.toString()
            val address = addressEditText.text.toString()
            val imageUrl = imageEditText.text.toString()
            val latitude = latEditText.text.toString().toDouble()
            val longitude = lngEditText.text.toString().toDouble()
            val wipLocation = WipLocation(label, address, imageUrl, latitude, longitude)
            callback.onPositiveButtonClick(wipLocation)
            this.dismiss()
        }
    }

    private fun setupEditTexts(view: View) {
        labelEditText = view.findViewById(R.id.label_edit_text)
        addressEditText = view.findViewById(R.id.address_edit_text)
        imageEditText = view.findViewById(R.id.image_url_edit_text)
        latEditText = view.findViewById(R.id.lat_edit_text)
        lngEditText = view.findViewById(R.id.lng_edit_text)
        latEditText.inputType = InputType.TYPE_NUMBER_FLAG_SIGNED
        lngEditText.inputType = InputType.TYPE_NUMBER_FLAG_SIGNED

        if (isEditMode) {
            labelEditText.setText(
                arguments?.getString(LocationDetailsActivity.LABEL) ?: WipApplication.EMPTY_STRING
            )
            labelEditText.isEnabled = false
            addressEditText.setText(
                arguments?.getString(LocationDetailsActivity.ADDRESS) ?: WipApplication.EMPTY_STRING
            )
            imageEditText.setText(
                arguments?.getString(LocationDetailsActivity.IMAGE_URL)
                    ?: WipApplication.EMPTY_STRING
            )
            latEditText.setText(
                arguments?.getString(LocationDetailsActivity.LATITUDE)
                    ?: WipApplication.EMPTY_STRING
            )
            lngEditText.setText(
                arguments?.getString(LocationDetailsActivity.LONGITUDE)
                    ?: WipApplication.EMPTY_STRING
            )
        }
    }

    fun setLocationDialogCallback(callback: LocationDialogCallback) {
        this.callback = callback
    }
}
