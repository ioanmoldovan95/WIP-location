package com.example.wiplocation.base

import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.wiplocation.WipApplication

open class BaseActivity : AppCompatActivity(), BaseView {

    override fun showErrorMessage(errorMessage: String) {
        Toast.makeText(applicationContext, errorMessage, Toast.LENGTH_LONG).show()
    }

    override fun getWipApplication(): WipApplication {
        return (application as WipApplication)
    }
}