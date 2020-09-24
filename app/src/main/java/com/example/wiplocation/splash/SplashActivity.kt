package com.example.wiplocation.splash

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.example.wiplocation.R
import com.example.wiplocation.base.BaseActivity
import com.example.wiplocation.location_list.LocationListActivity

class SplashActivity : BaseActivity(), SplashView {

    private lateinit var presenter: SplashPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        presenter = SplashPresenter(this)
        presenter.getLocationsList()
    }

    override fun goToLocationsListActivity(hasInternetConnection: Boolean) {
        Toast.makeText(applicationContext, "Locations list fetched successfully", Toast.LENGTH_LONG).show()
        val intent = Intent(this, LocationListActivity::class.java)
        intent.putExtra(HAS_INTERNET_CONNECTION, hasInternetConnection)
        startActivity(intent)
    }

    override fun onNoNetworkConnectivity() {
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
        alertDialogBuilder.create().show()
    }

    companion object {
        const val HAS_INTERNET_CONNECTION ="has_internet_connection"
    }
}