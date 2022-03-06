package com.example.todolist.addtodo

import android.Manifest
import android.app.Application
import android.content.pm.PackageManager
import android.location.Location
import android.os.Looper
import android.util.Log
import androidx.core.app.ActivityCompat
import androidx.lifecycle.*
import com.example.todolist.database.Todo
import com.example.todolist.database.TodoDatabaseDao
import com.google.android.gms.location.*

import kotlinx.coroutines.launch
import java.util.*

class AddTodoViewModel(dataSource: TodoDatabaseDao,
                       application: Application) : ViewModel() {
    // FusedLocationProviderClient - Main class for receiving location updates.
    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient

    // LocationRequest - Requirements for the location updates, i.e.,
    // how often you should receive updates, the priority, etc.
    private lateinit var locationRequest: LocationRequest

    // LocationCallback - Called when FusedLocationProviderClient
    // has a new Location
    private lateinit var locationCallback: LocationCallback


    /**
     * Hold a reference to TodoDatabase via Dao
     */
    val database = dataSource
    val app = application

    var due_date = MutableLiveData<Date>()
    var created_date = MutableLiveData<Date>()
    var location = MutableLiveData<String>()

    init {
        created_date.value = Date()
        // Set the default value of due_date to tomorrow
        var dt = Date()
        val c = Calendar.getInstance()
        c.time = dt
        c.add(Calendar.DATE, 1)
        dt = c.time
        due_date.value = dt
        location.value = "(25.10312, 121.52248)"
        // Initialize location-related objects
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(application)
        locationRequest = LocationRequest.create().apply {
            // Sets the desired interval for
            // active location updates.
            // This interval is inexact.
            interval = 30000L

            // Sets the fastest rate for active location updates.
            // This interval is exact, and your application will never
            // receive updates more frequently than this value
            fastestInterval = 10000L

            // Sets the maximum time when batched location
            // updates are delivered. Updates may be
            // delivered sooner than this interval
            maxWaitTime = 60000L

            priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        }

        locationCallback = object : LocationCallback() {
            override fun onLocationResult(locationResult: LocationResult) {
                locationResult ?: return
                for (location in locationResult.locations){
//                    longitude = String.format("%.5f",location.longitude)
//                    latitude = String.format("%.5f",location.latitude)
                    Log.i("Location", "lat: ${location.latitude}, long: ${location.longitude}")
                }
            }

        }

        fusedLocationProviderClient.requestLocationUpdates(locationRequest, locationCallback, Looper.getMainLooper())
    }

    fun submit(title: String, description: String) {
        viewModelScope.launch {
            val newTodo = Todo(title = title.trim(), description = description.trim(), due_date = due_date.value!!, created_date = created_date.value!!, location = location.value!!)
            insert(newTodo)
        }
    }

    private suspend fun insert(todo: Todo) {
        database.insert(todo)
    }

    fun reload() {
        if (ActivityCompat.checkSelfPermission(
                app,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                app,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            // Without permission to the GPS
            location.value = "None"
            return
        }
        fusedLocationProviderClient.lastLocation
            .addOnSuccessListener { loc : Location? ->
                loc?.let {
                    location.value = "(${String.format("%.5f",loc.latitude)}, ${String.format("%.5f",loc.longitude)})"
                } ?: run {
                    location.value = "None"
                }
            }
    }

    /**
     * Navigation
     */
    private val _navigateToHome = MutableLiveData<Boolean?>()

    val navigateToHome: LiveData<Boolean?>
        get() = _navigateToHome

    fun onCancel() {
        _navigateToHome.value = true
    }

    fun onCreated() {
        _navigateToHome.value = true
    }

    fun doneNavigation() {
        _navigateToHome.value = null
    }

}