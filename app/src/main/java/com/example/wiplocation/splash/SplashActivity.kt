package com.example.wiplocation.splash

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.wiplocation.R
import com.example.wiplocation.WipApplication
import com.example.wiplocation.model.Location

class SplashActivity : AppCompatActivity(), SplashView {

    lateinit var presenter: SplashPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        presenter = SplashPresenter(this)
        presenter.getLocationsList()
    }

    override fun onGetLocationsListSuccess(locationsList: ArrayList<Location>) {
        Toast.makeText(applicationContext, "Locations list fetched successfully", Toast.LENGTH_LONG).show()
    }

    override fun onGetLocationsListFailure(errorMessage: String) {
        Toast.makeText(applicationContext, errorMessage, Toast.LENGTH_LONG).show()
    }

    override fun getWipApplication(): WipApplication {
        return (application as WipApplication)
    }

    override fun onNoNetworkConectivity() {
        val alertDialogBuilder = AlertDialog.Builder(this)
        with(alertDialogBuilder) {
            setTitle(R.string.network_error_dialog_title)
            setMessage(R.string.network_error_dialog_description)
            setPositiveButton(R.string.retry) { _, _ ->
                presenter.getLocationsList()
            }
            setNegativeButton(R.string.exit) {
                _, _ -> finish()
            }
        }
        alertDialogBuilder.show()
    }
}