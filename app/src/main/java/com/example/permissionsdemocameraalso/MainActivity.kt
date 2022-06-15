package com.example.permissionsdemocameraalso

/**
 * This app is a Demo of how to check for permissions in your app.
 * Add the permissions to your manifest file, and from there, continue with the code
 * below.
 */

import android.Manifest
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat.shouldShowRequestPermissionRationale

class MainActivity : AppCompatActivity() {

    // variable declared to check is camera permission is granted
    // method needed to check permissions
    private val cameraLocationResultLauncher: ActivityResultLauncher<String> =
        registerForActivityResult(
            ActivityResultContracts.RequestPermission()) {
                isGranted ->
            if (isGranted) {
                Toast.makeText(this,
                    "Permission granted for camera.", Toast.LENGTH_LONG).show()
            } else {
                Toast.makeText(this,
                    "Permission denied for camera.", Toast.LENGTH_LONG).show()
            }
        }

    // variable declared to check is camera and location permission is granted
    // method needed to check multiple permissions
    private val cameraAndLocationResultLauncher: ActivityResultLauncher<Array<String>> =
        registerForActivityResult(
            ActivityResultContracts.RequestMultiplePermissions()) {
                permissions ->
            permissions.entries.forEach{
                val permissionName = it.key
                val isGranted = it.value
                if(isGranted) {
                    if(permissionName == Manifest.permission.ACCESS_FINE_LOCATION) {
                        Toast.makeText(this,"Permission granted for the location",
                        Toast.LENGTH_LONG).show()
                    } else if (permissionName == Manifest.permission.ACCESS_COARSE_LOCATION){
                        Toast.makeText(this, "Permission denied for Coarse Location",
                            Toast.LENGTH_LONG).show()
                    } else {
                        Toast.makeText(this, "Permission granted for Camera",
                            Toast.LENGTH_LONG).show()
                    }
                } else {
                    if(permissionName == Manifest.permission.ACCESS_FINE_LOCATION) {
                        Toast.makeText(this,"Permission denied for Fine location",
                            Toast.LENGTH_LONG).show()
                    } else if (permissionName == Manifest.permission.ACCESS_COARSE_LOCATION){
                        Toast.makeText(this, "Permission denied for Coarse Location",
                            Toast.LENGTH_LONG).show()
                    } else {
                        Toast.makeText(this, "Permission denied for Camera",
                            Toast.LENGTH_LONG).show()
                    }

                }
            }
        }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // button required to launch
        val btnCameraPermission: Button = findViewById<Button>(R.id.btnCameraPermission)

        // use the button to check the version of android and sdk we are using in order to
        // launch our camera and check permission
        // if app doesn't have access to permission, it should show why
        btnCameraPermission.setOnClickListener {
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M &&
                    shouldShowRequestPermissionRationale(Manifest.permission.CAMERA)) {
                showRationaleDialog("Permission Demo requires camera access",
                "Camera cannot be used because Camera access is denied")
            } else {
                cameraAndLocationResultLauncher.launch(
                    arrayOf(Manifest.permission.CAMERA,
                    Manifest.permission.ACCESS_FINE_LOCATION))
            }
        }
    }

    // show the alert dialog box with a title and a message
    private fun showRationaleDialog(
        title: String,
        message: String
    ) {
        val builder: AlertDialog.Builder = AlertDialog.Builder(this)
        builder.setTitle(title)
            .setMessage(message)
            .setPositiveButton("Cancel") {dialog, _->
                dialog.dismiss()
            }
        builder.create().show()
    }
}