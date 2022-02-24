package com.example.todolist.tododetail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.todolist.database.Todo
import com.example.todolist.database.TodoDatabaseDao
import kotlinx.coroutines.launch

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
     * Delete handler
     */
    fun onDelete() {
        viewModelScope.launch {
            delete()
        }
        onClose()
    }
    private suspend fun delete() {
        database.delete(todoKey)
    }

    /**
     * Navigation
     */
    private val _navigateToHome = MutableLiveData<Boolean?>()
    val navigateToHome: LiveData<Boolean?>
        get() = _navigateToHome

    fun doneNavigation() {
        _navigateToHome.value = null
    }

    fun onClose() {
        _navigateToHome.value = true
    }
}