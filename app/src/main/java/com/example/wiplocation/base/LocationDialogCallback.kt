package com.example.wiplocation.base

import com.example.wiplocation.model.WipLocation

interface LocationDialogCallback {
    fun onPositiveButtonClick(location:WipLocation)
}