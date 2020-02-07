package com.retar.go4lunch.base

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.provider.Settings.ACTION_APPLICATION_DETAILS_SETTINGS
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import com.retar.go4lunch.R
import dagger.android.support.DaggerAppCompatActivity


abstract class LocationPermissionActivity : DaggerAppCompatActivity(), ProvideNavController {

    private var requestDialog: AlertDialog? = null

    override fun getNavController(): NavController {
        return findNavController(R.id.nav_host)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (!permissionsGranted()) requestPermissions()

    }

    private fun permissionsGranted(): Boolean {
        return (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED)
    }

    private fun requestPermissions() {
        ActivityCompat.requestPermissions(
            this,
            arrayOf(
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ),
            LOCATION_PERMISSION_REQUEST_CODE
        )
    }


    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            LOCATION_PERMISSION_REQUEST_CODE -> {
                if (grantResults.isNotEmpty()
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED
                ) {
                    requestDialog?.dismiss()
                } else {
                    showRationaleForPermissions()
                }
            }
        }
    }

    private fun showRationaleForPermissions() {
        //Todo extract strings
        if (shouldShowRequestPermissionRationale(Manifest.permission.ACCESS_FINE_LOCATION)) {
            showPermissionsRequiredDialog(
                "Permission to access location is needed for the app to work.",
                dontAskAgainChecked = false
            )
        } else {
            showPermissionsRequiredDialog(
                "You previously denied location permission. Click ok to open settings to enable it.",
                dontAskAgainChecked = true
            )
        }
    }

    private fun showPermissionsRequiredDialog(body: String, dontAskAgainChecked: Boolean) {
        requestDialog = AlertDialog.Builder(this)
            .setMessage(body)
            //Todo extract strings
            .setTitle("Permission required")
            .setPositiveButton("OK") { _, _ ->
                if (!dontAskAgainChecked) requestPermissions() else openSettings()
            }
            .setNegativeButton("EXIT") { _, _ ->
                finish()
            }
            .setCancelable(false)
            .create()
        requestDialog?.show()
    }

    private fun openSettings() {
        //todo handle user return to app so the map fragment is shown
        val intent = Intent(ACTION_APPLICATION_DETAILS_SETTINGS)
        intent.data = Uri.parse("package:$packageName")
        startActivity(intent)
    }


    companion object {
        const val LOCATION_PERMISSION_REQUEST_CODE = 15
    }

    override fun onStart() {
        super.onStart()
        if (!permissionsGranted()) requestPermissions()

    }

    override fun onPause() {
        super.onPause()
        requestDialog?.dismiss()
    }


    override fun onSupportNavigateUp() =
        Navigation.findNavController(this, R.id.nav_host).navigateUp()
}


