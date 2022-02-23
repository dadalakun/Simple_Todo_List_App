package com.example.todolist.home

import android.app.Application
import android.util.Log
import androidx.lifecycle.*
import com.example.todolist.database.Todo
import com.example.todolist.database.TodoDatabaseDao
import com.example.todolist.ObjectWrapper

class HomeViewModel(dataSource: TodoDatabaseDao,
                    application: Application) : ViewModel() {

    var quote = "placeholder"

    /**
     * Hold a reference to TodoDatabase via Dao
     */
    val database = dataSource
    val todos = database.getAllTodos()

    /**
     * Navigation for the AddTodo fragment
     */
    private val _navigateToAddTodo = MutableLiveData<Boolean?>()
    val navigateToAddTodo: LiveData<Boolean?>
        get() = _navigateToAddTodo

    fun onAddTodoClicked() {
        _navigateToAddTodo.value = true
    }

    fun doneNavigation() {
        _navigateToAddTodo.value = null
    }

    /**
     * Navigation for the TodoDetail fragment
     */
    private val _navigateToTodoDetail = MutableLiveData<Long?>()
    val navigateToToDoDetail
        get() = _navigateToTodoDetail

    fun onTodoClicked(id: Long) {
        _navigateToTodoDetail.value = id
    }

    fun onTodoDetailNavigated() {
        _navigateToTodoDetail.value = null
    }

    init {
        val obj = ObjectWrapper()
        quote = obj.QuoteToday()
    }

}