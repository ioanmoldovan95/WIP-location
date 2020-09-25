package com.example.wiplocation.location_details

import com.example.wiplocation.base.BaseView
import com.example.wiplocation.model.WipLocation

interface LocationDetailsView : BaseView{
    fun onLocationFetchedSuccess(location:WipLocation)
}
