package com.retar.go4lunch.base

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.provider.Settings.ACTION_APPLICATION_DETAILS_SETTINGS
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.retar.go4lunch.R
import dagger.android.support.DaggerAppCompatActivity


abstract class LocationPermissionActivity : DaggerAppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

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
                    Toast.makeText(this, "teste", Toast.LENGTH_LONG).show()
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
        val builder = AlertDialog.Builder(this)
        builder.setMessage(body)
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
            .show()
    }

    private fun openSettings() {
        //todo handle user return to app so the map fragment is shown
        val intent = Intent(ACTION_APPLICATION_DETAILS_SETTINGS)
        intent.data = Uri.parse("package:$packageName")
        startActivity(intent)
    }


    companion object {
        const val LOCATION_PERMISSION_REQUEST_CODE = 15
        const val ZOOM_MODE = 15f

    }

    override fun onStart() {
        super.onStart()
        if (!permissionsGranted()) requestPermissions()

    }
}
