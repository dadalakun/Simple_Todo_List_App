package com.example.todolist.addtodo

import android.app.Application
import android.util.Log
import androidx.lifecycle.*
import com.example.todolist.convertDateToString
import com.example.todolist.database.Todo
import com.example.todolist.database.TodoDatabaseDao
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

class AddTodoViewModel(dataSource: TodoDatabaseDao,
                       application: Application) : ViewModel() {
    /**
     * Hold a reference to TodoDatabase via Dao
     */
    val database = dataSource

    var due_date = MutableLiveData<Date>()
    var created_date = MutableLiveData<Date>()


    init {
        created_date.value = Date()
        // Set the default value of due_date to tomorrow
        var dt = Date()
        val c = Calendar.getInstance()
        c.time = dt
        c.add(Calendar.DATE, 1)
        dt = c.time
        due_date.value = dt
    }

    fun submit(title: String, description: String) {
        viewModelScope.launch {
            val newTodo = Todo(title = title, description = description, due_date = due_date.value!!, created_date = created_date.value!!)
            insert(newTodo)
        }
    }

    private suspend fun insert(todo: Todo) {
        database.insert(todo)
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