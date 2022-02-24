package com.example.todolist.addtodo

import android.Manifest
import android.app.Application
import android.content.pm.PackageManager
import android.location.Location
import androidx.core.app.ActivityCompat
import androidx.lifecycle.*
import com.example.todolist.database.Todo
import com.example.todolist.database.TodoDatabaseDao
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import kotlinx.coroutines.launch
import java.util.*

class AddTodoViewModel(dataSource: TodoDatabaseDao,
                       application: Application) : ViewModel() {
    private lateinit var fusedLocationClient: FusedLocationProviderClient
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
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(application)
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
        fusedLocationClient.lastLocation
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