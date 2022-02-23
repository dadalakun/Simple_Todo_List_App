package com.example.todolist

import android.Manifest
import android.content.pm.PackageManager
import android.location.Location
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.core.app.ActivityCompat
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices

class MainActivity : AppCompatActivity() {
    private lateinit var fusedLocationClient: FusedLocationProviderClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

//        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
//        Log.i("mumi", "before get location")
//
//
//        fusedLocationClient.lastLocation
//            .addOnSuccessListener { location : Location? ->
//                Log.i("mumi", "${location?.latitude} ${location?.longitude}")
//            }
    }
}