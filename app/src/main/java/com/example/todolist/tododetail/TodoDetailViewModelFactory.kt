package com.example.todolist.tododetail

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.todolist.database.TodoDatabaseDao

class TodoDetailViewModelFactory (
    private val todoKey: Long,
    private val dataSource: TodoDatabaseDao) : ViewModelProvider.Factory {

        @Suppress("unchecked_cast")
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(TodoDetailViewModel::class.java)) {
                return TodoDetailViewModel(todoKey, dataSource) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
}