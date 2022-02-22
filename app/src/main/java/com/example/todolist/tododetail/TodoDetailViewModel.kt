package com.example.todolist.tododetail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.todolist.database.Todo
import com.example.todolist.database.TodoDatabaseDao

class TodoDetailViewModel (private val todoKey: Long = 0L,
                           dataSource: TodoDatabaseDao) : ViewModel() {

    /**
     * Hold a reference to TodoDatabase via its Dao
     */
    val database = dataSource

    private val todo: LiveData<Todo>

    fun getTodo() = todo

    init {
        todo = database.getTodoWithId(todoKey)
    }

    /**
     * Variable that tells the fragment whether it should navigate to [HomeFragment]
     */
    private val _navigateToHome = MutableLiveData<Boolean?>()

    /**
     * When true immediately navigate back to the [HomeFragment]
     */
    val navigateToHome: LiveData<Boolean?>
        get() = _navigateToHome

    /**
     * Call this immediately after navigating to [HomeFragment]
     */
    fun doneNavigation() {
        _navigateToHome.value = null
    }

    fun onClose() {
        _navigateToHome.value = true
    }
}