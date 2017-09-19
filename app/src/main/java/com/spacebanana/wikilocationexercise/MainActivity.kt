package com.spacebanana.wikilocationexercise

import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.databinding.DataBindingUtil
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.view.View
import android.widget.Toast
import com.google.android.gms.location.LocationServices
import com.google.gson.JsonObject
import com.spacebanana.wikilocationexercise.databinding.ActivityMainBinding
import retrofit2.Call
import retrofit2.Callback

class MainActivity : AppCompatActivity() {

    private val  REQUEST_LOCATION: Int = 12
    private var binding: ActivityMainBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding?.allowBtn?.setOnClickListener({ askForPermissions() })
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        when(requestCode) {
            REQUEST_LOCATION -> {
                if (grantResults.isNotEmpty()
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    getCurrentLocation()
                } else {
                    Toast.makeText(this, "No fun, no fun", Toast.LENGTH_SHORT).show()
                }

                return
            }
        }
    }

    private fun askForPermissions() {
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    Array(1){Manifest.permission.ACCESS_COARSE_LOCATION},
                    REQUEST_LOCATION)
        } else {
            getCurrentLocation()
        }
    }

    @SuppressLint("MissingPermission")
    private fun getCurrentLocation() {
        val fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
        (fusedLocationClient)?.lastLocation?.addOnSuccessListener { location ->
            binding?.message?.setText("Your location is:\n" + location.latitude + "\n" + location.longitude)
            binding?.allowBtn?.setText("Fetch closest wiki")

            binding?.allowBtn?.setOnClickListener({ fetchClosestWiki() })
        }
    }

    private fun fetchClosestWiki() {
//        WikiApiClient.create().searchNearby(
//
//        ).enqueue({
//            success ->
//        })
    }
}

