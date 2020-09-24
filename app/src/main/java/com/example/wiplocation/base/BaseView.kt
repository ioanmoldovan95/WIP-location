package com.example.wiplocation.base

import com.example.wiplocation.WipApplication

interface BaseView {
    fun getWipApplication(): WipApplication
    fun showErrorMessage(errorMessage: String)
}